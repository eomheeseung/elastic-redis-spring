package example.tech_merge.service;

import example.tech_merge.basicSearch.BasicSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class EsService {
    private final RestHighLevelClient client;
    private final BasicSearch search;

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

    public void deleteDocument(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        client.delete(request, RequestOptions.DEFAULT);
    }


    public JSONObject searchQuest(String index) throws IOException {
        SearchResponse searchResponse = search.searchQuest(index);
        // 실제 데이터를 확인하기 위해서는 hits의 sources를 확인해야 하기 때문에..
        SearchHits hits = searchResponse.getHits();
        // SearchHit[] 배열을 가져와서
        SearchHit[] searchHits = hits.getHits();

        // return할 json 생성
        JSONObject jsonObject = new JSONObject();

        // searchHits가 array이므로 jsonArray 생성
        JSONArray jsonArray = new JSONArray();
        // jsonArray에 searchHits 담기
        jsonArray.addAll(Arrays.asList(searchHits));

        // jsonObject에 jsonArray 담기
        jsonObject.put("hits", jsonArray);

        return jsonObject;
    }
}
