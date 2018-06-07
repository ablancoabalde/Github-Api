package com.alberto;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class Logic {

    private static Repository repository;

    /**
     * Guarda el directorio para luego trabajar con él
     *
     * @param directorio
     * @throws IOException
     */
    public static void setRepository(File directorio) throws IOException {

        repository = Git.open(new File(directorio.getAbsolutePath() + "/.git")).checkout().getRepository();
    }

    /**
     * Inicializa un repositorio
     */
    public static void initRepository() {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();

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

    /**
     * Hacer un commit del repositorio seleccionado. Pide un texto como
     * descripción del commit.
     *
     * @param text
     *
     */
    public static void commitRepository(String text) {

        Git git = new Git(repository);

        try {
            git.commit()
                    .setMessage(text)
                    .call();
            JOptionPane.showMessageDialog(null, "El Repositorio ha añadido el commit correctamente.");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Ha habido un error al hacerle commit al Repositorio.");
        }
    }
}
