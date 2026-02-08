package org.example.moneyflowspring.known_merchants;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KnownMerchantsFileReader {

    private final KnownMerchantsRepository knownMerchantsRepository;
    private final KnownMerchantMapper knownMerchantMapper;

    private final static String KNOWN_MERCHANTS_INPUT_DIR = "C:\\Users\\piotr\\Desktop\\EXPENSE_TRACKER\\known_merchants";

    ArrayList<String> listBaseFileNames() {
        File inputDirectory = new File(KNOWN_MERCHANTS_INPUT_DIR);
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

    List<KnownMerchantFileRecord> readFile(String fileName) {
        List<KnownMerchantFileRecord> recordLines = new ArrayList<>();
        final String file = KNOWN_MERCHANTS_INPUT_DIR + "\\" + fileName;
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            br.readLine(); //skip 1st line
            String line = br.readLine();
            while (line != null) {
                recordLines.add(new KnownMerchantFileRecord(line));
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println(e);
        }
        return recordLines;
    }

    NewMerchantsFromFile retrieveKnownMerchantsFiles() {
        ArrayList<String> files = listBaseFileNames();
        List<KnownMerchantEntity> savedMerchants = new LinkedList<>();
        List<KnownMerchantEntity> duplicatedMerchants = new LinkedList<>();
        System.out.println("Processing " + files.size() + " files.");
        for (String file : files) {
            List<KnownMerchantFileRecord> knownMerchantFileRecords = readFile(file);
            Map<String, KnownMerchantEntity> collectedMerchants = knownMerchantFileRecords
                    .stream()
                    .map(knownMerchantMapper::merchantEntityFromFileRecord)
                    .distinct()
                    .collect(Collectors.toMap(KnownMerchantEntity::getMerchantCode, Function.identity()));

            knownMerchantFileRecords.forEach(record -> {
                KnownMerchantKeyWordEntity keyword = new KnownMerchantKeyWordEntity(null, null, record.getKeyword(), record.getWeight());
                KnownMerchantEntity knownMerchantEntity = collectedMerchants.get(record.getMerchantCode());
                if (knownMerchantEntity != null) {
                    knownMerchantEntity.addKeyword(keyword);
                }
            });
            for (KnownMerchantEntity knownMerchantEntity : collectedMerchants.values()) {
                try {
                    savedMerchants.add(knownMerchantsRepository.save(knownMerchantEntity));
                } catch (DataIntegrityViolationException e) {
                    duplicatedMerchants.add(knownMerchantEntity);
                }
            }
        }
        return new NewMerchantsFromFile(savedMerchants, duplicatedMerchants);
    }
}
