package project.repository;

/**
 * Repository Exception class which extends Runtime Exception
 */
public class RepositoryException extends RuntimeException {

    public RepositoryException(String message) {
        super(message);
    }
}
