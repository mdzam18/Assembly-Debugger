package src.DebugAdapter;

import com.google.gson.Gson;
import src.DapClasses.Response;
import src.DapClasses.Threads.Thread;
import src.DapClasses.Threads.ThreadsRequest;
import src.DapClasses.Threads.ThreadsResponse;

public class ThreadsManager {
    private String processThreadsRequest(Gson gson, String json) {
        ThreadsRequest request = gson.fromJson(json, ThreadsRequest.class);
        ThreadsResponse response = new ThreadsResponse();
        Thread[] threads = new Thread[1];
        threads[0] = new Thread();
        threads[0].setId(1);
        threads[0].setName("thread 1");
        response.setThreads(threads);
        Response r = new Response();
        r.setBody(response);
        r.setCommand("threads");
        r.setRequest_seq(request.getSeq());
        r.setSuccess(true);
        String jsonResponse = gson.toJson(r);
        return jsonResponse;
    }

    public String createThreadsResponse(String json, Gson gson, SendProtocolMessage send){
        String ThreadsRes = processThreadsRequest(gson, json);
        send.sendProtocolMessage(ThreadsRes);
        return ThreadsRes;
    }

}
