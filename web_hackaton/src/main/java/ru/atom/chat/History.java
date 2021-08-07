package ru.atom.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

@Repository
public class History {
    private static final Logger log = LoggerFactory.getLogger(History.class);
    private File historyFile;
    private FileWriter writer;

    public History() {
        historyFile = new File("src/main/resources/history.txt");
        try {
            writer = new FileWriter(historyFile,true);
        } catch (FileNotFoundException e) {
            log.warn("historyFile not found.");
        } catch (IOException e) {
            log.warn("IOException.");
        }
    }

    public File getHistoryFile() {
        return historyFile;
    }

    public void write(String line) {
        try {
            writer.write(line + '\n');
            writer.flush();
        } catch (FileNotFoundException e) {
            log.warn("historyFile not found.");
        } catch (NullPointerException e) {
            log.warn("NullPointerException");
        } catch (IOException e) {
            log.warn("IOException.");
        }
    }
}
