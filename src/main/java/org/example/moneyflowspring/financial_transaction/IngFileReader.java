package org.example.moneyflowspring.financial_transaction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Service
public class IngFileReader {

    @Value("${reader.savingacc}")
    private String SAVINGS_ACCOUNT_NR;
    @Value("${reader.directacc}")
    private String DIRECT_ACCOUNT_NR;
    @Value("${reader.directacc2}")
    private String DIRECT_ACCOUNT_NR_2;
    private final String CASHOUT_PREFIX = "Wypłata gotówki";
    private final String CASHIN_PREFIX_1 = "\" Wpłata gotówki";
    private final String CASHIN_PREFIX_2 = "\" Wpłatomat - wpłata własna";
    private final String KREDYT = "Uruchomienie kre";
    private final static String INPUT_DIR = "C:\\Users\\piotr\\Desktop\\EXPENSE_TRACKER\\input";
    private final static String OUTPUT_DIR = "C:\\Users\\piotr\\Desktop\\EXPENSE_TRACKER\\output";
    private final static String HEADINGS_LINE = "\"Data transakcji\";\"Data księgowania\"";


    private static int INNER_TRANSACTIONS = 0;


    boolean existsDirectory(final String s) {
        File f = new File(s);
        if (!f.exists()) return false;
        if (!f.isDirectory()) return false;
        return f.canRead();
    }

    ArrayList<String> listBaseFileNames() {
        File inputDirectory = new File(INPUT_DIR);
        String[] files = inputDirectory.list();
        ArrayList<String> resultFiles = new ArrayList<>();
        for (int i = 0; (files != null) && (i < files.length); i++) {
            File f = new File(files[i]);
            if (!f.isDirectory()) {
                resultFiles.add(f.getName());
            }
        }
        return resultFiles;
    }

    public List<FinancialTransactionFileRecord> retrieveNewTransactionsFromFiles() {
        int recordsTotal = 0;
        List<FinancialTransactionFileRecord> transactionRecords = new ArrayList<>();
        ArrayList<String> files = listBaseFileNames();
        System.out.println("Processing " + files.size() + " files.");
        List<String> recordsLines;
        for (String file : files) {
            recordsLines = readFile(file);
            recordsTotal += recordsLines.size();
            for (String line : recordsLines) {
                FinancialTransactionFileRecord transactionRecord = createNewRecordFromParserLine(line);
                if (transactionRecord == null) continue;
                transactionRecords.add(transactionRecord);
            }
        }
        System.out.println("FOUND TOTAL RECORDS " + recordsTotal);
        System.out.println("INNER TRANSACTIONS  " + INNER_TRANSACTIONS);
        return transactionRecords;
    }

    List<String> readFile(String fileName) {
        List<String> recordLines = new ArrayList<>();
        boolean headersFound = false;
        final String file = INPUT_DIR + "\\" + fileName;
        try (BufferedReader br = new BufferedReader(new FileReader(file, Charset.forName("Windows-1250")))) {
            String line = br.readLine();
            while (line != null) {

                if (!headersFound) {
                    headersFound = line.startsWith(HEADINGS_LINE);
                } else {
                    String validRecordRegex = "^\\d{4}-\\d{2}-\\d{2}";
                    if (line.length() > 13 && line.substring(0, 10).matches(validRecordRegex)) {
                        recordLines.add(line);
                    }
                }
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return recordLines;
    }


    boolean isInnerTransaction(String acc, String title, String merchant) {
        return SAVINGS_ACCOUNT_NR.equals(acc) || DIRECT_ACCOUNT_NR.equals(acc) || DIRECT_ACCOUNT_NR_2.equals(acc) || (title.contains("własny") && merchant.contains("DOMAGALSKI"));
    }

    FinancialTransactionFileRecord createNewRecordFromParserLine(String readLine) {
        String[] line = readLine.split(";");
        final String transaction_date = line[1];
        final String vendor_data = line[2];
        final String title = line[3];

        if (title.contains(KREDYT)) return null;

        TranType tranType = null;

        if (title.contains(CASHOUT_PREFIX)) {
            tranType = TranType.CASH_OUT;
        } else if (title.contains(CASHIN_PREFIX_1) ||
                title.contains(CASHIN_PREFIX_2)) {
            tranType = TranType.CASH_IN;
        }

        final String account = line[4];

        if (isInnerTransaction(account, title, vendor_data)) {
            INNER_TRANSACTIONS++;
            return null;
        }

        final String tran_nr = line[7];
        final String amount_string = line[8];
        final String blockAmount = line[10];

        Double amountValue = null;
        try {
            String amountString = amount_string.replace(",", ".");
            amountValue = Double.valueOf(amountString);
            if (tranType == null) {
                if (amountValue < 0) {
                    tranType = TranType.EXPENSE;
                } else {
                    tranType = TranType.INCOME;
                }
            }
        } catch (NumberFormatException e) {
            if (blockAmount == null || blockAmount.isBlank()) {
                System.err.println();
                System.err.println("Transakcja " + tran_nr + " została odrzucona- niepoprawny format kwoty");
            }

            return null;
        }

        return new FinancialTransactionFileRecord(
                tranType,
                transaction_date,
                tran_nr,
                account,
                vendor_data,
                title,
                amountValue
        );
    }
}
