package example;

import com.couchbase.client.dcp.message.DcpMutationMessage;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.JsonNode;
import com.couchbase.client.deps.com.fasterxml.jackson.databind.ObjectMapper;
import com.couchbase.client.deps.io.netty.buffer.ByteBuf;
import com.couchbase.connect.kafka.filter.Filter;

import java.io.IOException;

public class FieldFilter implements Filter {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public boolean pass(ByteBuf byteBuf) {
        if (DcpMutationMessage.is(byteBuf)) {
            ByteBuf content = DcpMutationMessage.content(byteBuf);
            if (content.readableBytes() > 0) {
                byte[] bytes = new byte[content.readableBytes()];
                content.getBytes(0, bytes);
                try {
                    JsonNode doc = MAPPER.readTree(bytes);
                    return "airline".equals(doc.get("type").asText());
                } catch (IOException e) {
                    return false;
                }
            }
        }
        return false;
    }
}
