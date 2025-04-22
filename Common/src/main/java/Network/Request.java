package Network;

import java.io.Serializable;

public record Request(String[] args, Object argument) implements Serializable {}