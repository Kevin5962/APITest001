package utils;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

import java.net.URI;

/**
 * Description:
 * Author: FengkaiXiao
 * Date: 2019-11-09
 * Time: 9:16
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {
    public static final String METHOD_NAME = "DELETE";

    @Override
    public String getMethod() {
        return METHOD_NAME;
    }
    public HttpDeleteWithBody(){
        super();
    }
    public HttpDeleteWithBody(final String uri){
        super();
        setURI(URI.create(uri));
    }
    public HttpDeleteWithBody(final URI uri){
        super();
        setURI(uri);
    }

}
