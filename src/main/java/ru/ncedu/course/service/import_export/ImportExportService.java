package ru.ncedu.course.service.import_export;

import ru.ncedu.course.LoyaltyBalance;
import ru.ncedu.course.service.properties.Property;

import java.io.*;
import java.util.Optional;

public class ImportExportService {

    @Property("dumpPath")
    public String path;

    public Optional<LoyaltyBalance> readLoyaltyBalance() {
        File dump = new File(path);
        if(dump.exists()) {
            try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(dump))) {
                return Optional.of((LoyaltyBalance) input.readObject());
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Could not load from file");
                e.printStackTrace();
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }

    public void writeLoyaltyBalance(LoyaltyBalance balance) {
        File dump = new File(path);
        try {
            dump.createNewFile();
            try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(dump, false))) {
                output.writeObject(balance);
            }
        } catch (IOException e) {
            System.out.println("Could not save to file");
            e.printStackTrace();
        }
    }

}
