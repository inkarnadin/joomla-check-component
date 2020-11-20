package web.cms.joomla;

import lombok.SneakyThrows;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import web.AbstractRequest;

public class JoomlaCheckComponentRequest extends AbstractRequest {

    @SneakyThrows
    public Response send(String... params) {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(params[0] + "://" + params[1] + "/administrator/components/" + params[2])
                .method(GET, null)
                .addHeader(USER_AGENT_HEADER, USER_AGENT_HEADER_VALUE)
                .build();
        Response response = client.newCall(request).execute();
        response.close();

        return response;
    }

}
