package DAP;

public interface InitializeRequest extends Request{
    String command = "initialize";

    InitializeRequestArguments arguments = null;
}
