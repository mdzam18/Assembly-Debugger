package DAP;

public interface Response extends ProtocolMessage{
    Type type = Type.response;

    /**
     * Sequence number of the corresponding request.
     */
    int request_seq = 0;

    /**
     * Outcome of the request.
     * If true, the request was successful and the 'body' attribute may contain
     * the result of the request.
     * If the value is false, the attribute 'message' contains the error in short
     * form and the 'body' may contain additional information (see
     * 'ErrorResponse.body.error').
     */
    boolean success = false;

    /**
     * The command requested.
     */
    String command = null;

    /**
     * Contains the raw error in short form if 'success' is false.
     * This raw error might be interpreted by the frontend and is not shown in the
     * UI.
     * Some predefined values exist.
     * Values:
     * 'cancelled': request was cancelled.
     * etc.
     */
    //message?: 'cancelled' | string;
    String message = null;
    /**
     * Contains request result if success is true and optional error details if
     * success is false.
     */
    Object body = null;
}
