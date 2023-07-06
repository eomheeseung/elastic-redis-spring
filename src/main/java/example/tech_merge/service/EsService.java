package example.tech_merge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EsService {
    private final RestHighLevelClient client;

    public void create() throws IOException {
        String indexName = "game";
        CreateIndexRequest request = new CreateIndexRequest(indexName);

        request.settings(Settings.builder()
                .put("index.number_of_shards", 1)
                .put("index.number_of_replicas", 0));

        client.indices().create(request, RequestOptions.DEFAULT);
    }

    public void createDocument(@NotNull String index, @NotNull String id, @NotNull String jsonBody)
            throws IOException {
        IndexRequest request = new IndexRequest(index)
                .id(id)
                .source(jsonBody, XContentType.JSON);
        client.index(request, RequestOptions.DEFAULT);
    }

    public GetResponse getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        return client.get(request, RequestOptions.DEFAULT);
    }

    public void updateDocument(@NotNull String index,
                               @NotNull String id,
                               @NotNull Map<String, Object> bodyMap) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(index, id).doc(bodyMap);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }
}
