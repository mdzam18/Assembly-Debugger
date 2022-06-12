package DAP;

public interface InitializeRequestArguments {
    /**
     * The ID of the (frontend) client using this adapter.
     */
    String clientID = null;

    /**
     * The human readable name of the (frontend) client using this adapter.
     */
    String clientName = null;

    /**
     * The ID of the debug adapter.
     */
    String adapterID = null;

    /**
     * The ISO-639 locale of the (frontend) client using this adapter, e.g. en-US
     * or de-CH.
     */
    String locale = null;

    /**
     * If true all line numbers are 1-based (default).
     */
    boolean linesStartAt1 = false;

    /**
     * If true all column numbers are 1-based (default).
     */
    boolean columnsStartAt1 = false;

    /**
     * Determines in what format paths are specified. The default is 'path', which
     * is the native format.
     * Values: 'path', 'uri', etc.
     */
    String pathFormat = null;

    /**
     * Client supports the optional type attribute for variables.
     */
    boolean supportsVariableType = false;

    /**
     * Client supports the paging of variables.
     */
    boolean supportsVariablePaging = false;

    /**
     * Client supports the runInTerminal request.
     */
    boolean supportsRunInTerminalRequest = false;

    /**
     * Client supports memory references.
     */
    boolean supportsMemoryReferences = false;

    /**
     * Client supports progress reporting.
     */
    boolean supportsProgressReporting = false;

    /**
     * Client supports the invalidated event.
     */
    boolean supportsInvalidatedEvent = false;

    /**
     * Client supports the memory event.
     */
    boolean supportsMemoryEvent = false;
}
