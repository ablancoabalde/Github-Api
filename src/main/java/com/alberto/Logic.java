package com.alberto;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class Logic {

    private static Repository repository;

    public static void setRepository(File directorio) throws IOException {

        repository=Git.open(new File(directorio.getAbsolutePath()+"/.git")).checkout().getRepository();
    }

    public static Repository getRepository() {
        return repository;
    }

    public static void initRepository() {
        FileRepositoryBuilder repositoryBuilder=new FileRepositoryBuilder();

        try {
            repositoryBuilder.setGitDir(repository.getDirectory().getCanonicalFile())
                    .readEnvironment()
                    .findGitDir()
                    .setMustExist(true)
                    .build();
            JOptionPane.showMessageDialog(null, "El Repositorio ha sido inicializado correctamente.");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, "Ha habido un error al inicializar el Repositorio.");
        }
    }
}
