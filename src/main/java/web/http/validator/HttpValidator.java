package web.http.validator;

import okhttp3.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class HttpValidator implements ResponseValidator {

    public static Boolean isRedirect(Response response) {
        if (Objects.nonNull(response.priorResponse())) {
            Response priorResponse = response.priorResponse();
            return Objects.nonNull(priorResponse) && priorResponse.code() == 301;
        }
        return false;
    }

    public static Boolean isOriginalSource(Response response, String originalHost) {
        Response prior = response.priorResponse();
        if (Objects.nonNull(prior) && prior.code() / 100 == 3) {
            String location = String.join("", prior.headers().values("Location"));
            return Pattern.compile(originalHost).matcher(location).find();
        }
        return true;
    }

    public static Boolean isHiddenNotFound(Response response) {
        List<Integer> codes = new ArrayList<>();

        Response resp = response;
        codes.add(resp.code());

        while (Objects.nonNull(resp.priorResponse())) {
            resp = resp.priorResponse();
            codes.add(resp.code());
        }

        return codes.contains(404);
    }

}
