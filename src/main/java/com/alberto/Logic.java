package com.alberto;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

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

    /**
     * Permite subir el repositorio a github. Pide la URL, nombre y contraseña
     * de la cuenta con la que se subira.
     *
     * @param httpURL
     * @param username
     * @param password
     * @throws URISyntaxException
     *
     */
    public static void pushRepository(String httpURL, String username, String password) throws URISyntaxException {

        try {
            Git git = new Git(repository);
            RemoteAddCommand remoteAddCommand = git.remoteAdd();
            remoteAddCommand.setName("origin");
            remoteAddCommand.setUri(new URIish(httpURL));
            remoteAddCommand.call();

            // Pushea y introduce los datos del usuario de git
            PushCommand pushCommand = git.push();
            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(username, password));
            pushCommand.call();

        } catch (GitAPIException ex) {
        }
    }

    /**
     * Permite clonar un repositorio de github. Pide la url del repositorio y el
     * directorio donde se guardara.
     *
     * @param repositoryURL
     * @param directorio
     *
     */
    public static void clonar(String repositoryURL, File directorio) {
        try {
            Git.cloneRepository()
                    .setURI(repositoryURL)
                    .setDirectory(directorio)
                    .call();
            JOptionPane.showMessageDialog(null, "Repositorio clonado correctamente.");

        } catch (GitAPIException ex) {

            JOptionPane.showMessageDialog(null, "Ha habido un error al clonar el Repositorio.");
        }
    }

}
