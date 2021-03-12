import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.ClassRule;
import org.junit.Rule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.http.ResponseDefinition.ok;
public class Mock {




    public static WireMockRule wireMockRule = new WireMockRule();

//    public static void getAllEmployee() {
//        // задаем обработчик запроса
//        stubFor(get(urlEqualTo("/get_employee")) // обработчик получает запросы по ссылке /get_employee
//                .willReturn(aResponse() // выдает ответ со статусом 200 (OK)
//                        .withHeader("Content-Type", "application/json") // с типом данных application/json
//                        .withBodyFile("employee/get_all_employee.json"))); // с телом ответа, записанном в файле
//    }
    public static void main(String[] args) {
        WireMockServer wireMockServer = new WireMockServer(WireMockConfiguration.options().extensions(new ResponseTemplateTransformer(true))); //No-args constructor will start on port 8080, no HTTPS
    /*
    Устанавливаем обработчики ответов для запросов
    (в обработчиках ответов проверяется содержимое
    запросов и выдаются ошибки в ответ в случае их наличия,
    иначе запрос обрабатывается штатно самим методом запроса)
     */

        wireMockServer.start();
        stubFor(get(urlEqualTo("/mock/get_employee"))
//                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{"+
                                "\"id\": \"1024\"," +
                                "\"fio\": \"Иванов Иван Иавнович\","+
                                "\"position\": \"Специалист по тестированию\"," +
                                "\"number\": \"101\""+
                                "}")));

// я правлю ковычки
//       .withBody("{" +
//                "\"id\": \" {id из запроса}\"," +
//                "\"status\": \"DELETED\"" +
//                " }")));




//        {{randomValue type='UUID'}}
//        stubFor(get(urlEqualTo("/getjson"))
//                .willReturn(aResponse()
//                        .withStatus(200)
//                        .withHeader("Content-Type", "application/json")
//                        .withBody("{\n" +
//                                "  \"request\": {\n" +
//                                "    \"method\": \"GET\",\n" +
//                                "    \"url\": \"/getjson\"\"\n" +
//                                "  },\n" +
//                                "  \"response\": {\n" +
//                                "    \"parameter1\" : \"hello\",\n" +
//                                "    \"parameter2\": \"world\"\n" +
//                                "  }\n" +
//                                "}")));

        stubFor(delete(urlPathEqualTo("/mock/delete_employee"))
//                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"id\": \"{{request.query.id}}\"," +
                                "\"status\": \"DELETED\"" +
                                " }")));


        stubFor(post(urlEqualTo("/mock/create_employee"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{" +
                                "\"fio\": \"{{jsonPath request.body '$.fio'}}\","+
                                "\"position\": \"{{jsonPath request.body '$.position'}}\"," +
                                "\"number\": \"{{jsonPath request.body '$.number'}}\"," +
                                "\"id\": \"{{randomValue type='UUID'}}\"" +
                                "}" )));
    }
}



//                        .withTransformers("create-vacation-validation")
//                ));
//
//
//
//    }
//}
//}
