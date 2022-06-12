package DAP;

public interface InitializeResponse extends Response{
    /**
     * The capabilities of this debug adapter.
     */
    Capabilities body = null;
}
