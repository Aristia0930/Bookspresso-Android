package com.ssafy.cafe.gpt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GptConfig {

    private String bookList="[\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-01-0\",\n" +
            "    \"title\": \"The Dragon's Legacy\",\n" +
            "    \"author\": \"Alistair Finch\",\n" +
            "    \"summary\": \"고대 드래곤의 비밀을 둘러싼 마법과 유산의 판타지 대서사시입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FThe%20Dragon's%20Legacy.png?alt=media&token=97912987-8b95-4854-9eed-b6453825e91f\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-02-7\",\n" +
            "    \"title\": \"Echoes of Andromeda\",\n" +
            "    \"author\": \"Dr. Evelyn Reed\",\n" +
            "    \"summary\": \"안드로메다에서 들려오는 신호를 따라 인류의 기원을 추적하는 과학적 모험 이야기입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FEchoes%20of%20Andromeda.png?alt=media&token=5a7cb4ad-6450-4af3-8599-53ab3aad3a61\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-03-4\",\n" +
            "    \"title\": \"Parisian Hearts\",\n" +
            "    \"author\": \"Sophie Dubois\",\n" +
            "    \"summary\": \"파리의 로맨틱한 거리에서 펼쳐지는 사랑과 자아 찾기의 드라마입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FParisian%20Hearts.png?alt=media&token=c51795d3-a802-4819-86b9-cb512e978bce\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-04-1\",\n" +
            "    \"title\": \"The Clockwork Cipher\",\n" +
            "    \"author\": \"Kenji Tanaka\",\n" +
            "    \"summary\": \"기계 장치와 비밀 암호가 숨겨진 스팀펑크 모험이 펼쳐지는 미스터리 소설입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FThe%20Clockwork%20Cipher.png?alt=media&token=77d487a2-94fc-4e2a-b6d2-51001d0355b2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-08-9\",\n" +
            "    \"title\": \"A Taste of the World\",\n" +
            "    \"author\": \"Javier Hernandez\",\n" +
            "    \"summary\": \"전 세계 다양한 요리를 통해 문화를 탐험하는 미식가의 여정을 담은 이야기입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FA%20Taste%20of%20the%20World.png?alt=media&token=5d939821-b32d-44d9-9a94-1a638a1b9f15\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-0-123456-11-9\",\n" +
            "    \"title\": \"Cybernetic Ronin\",\n" +
            "    \"author\": \"Akira Sato\",\n" +
            "    \"summary\": \"사이버 시대의 외로운 전사가 미래 도시에서 정의를 실현하는 액션 SF 소설입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2FCybernetic%20Ronin.png?alt=media&token=d483830d-bace-44c6-80c3-35b06577f4a1\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-259-574355-2\",\n" +
            "    \"title\": \"잃어버린 꿈의 조각\",\n" +
            "    \"author\": \"정우성\",\n" +
            "    \"summary\": \"꿈속에서만 만날 수 있는 연인. 현실과 꿈의 경계가 모호해지면서, 그들의 사랑은 점점 더 깊어지고 위험해진다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F잃어버린%20꿈의%20조각.png?alt=media&token=db38ccdb-4979-4035-9ca8-a12c74aa2a34\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-288-871987-3\",\n" +
            "    \"title\": \"잊혀진 노래\",\n" +
            "    \"author\": \"오준혁\",\n" +
            "    \"summary\": \"시간을 되돌릴 수 있는 능력을 가진 소녀. 과거의 실수를 바로잡으려 하지만, 예상치 못한 결과들이 그녀를 기다린다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F잊혀진%20노래.png?alt=media&token=834ae67f-b8d9-4d11-aadb-0a07936ad621\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-494-497996-1\",\n" +
            "    \"title\": \"환상의 숲\",\n" +
            "    \"author\": \"한지민\",\n" +
            "    \"summary\": \"사막 한가운데서 발견된 고대 유적. 고고학 팀은 그곳에서 인류의 기원에 대한 충격적인 비밀을 마주하게 된다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F환상의%20숲.png?alt=media&token=e326ab7c-95b8-41c6-9a22-dd024a7fc16f\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-583-901332-9\",\n" +
            "    \"title\": \"별을 쫓는 아이\",\n" +
            "    \"author\": \"장동건\",\n" +
            "    \"summary\": \"폐쇄된 미술관에서 벌어진 기묘한 살인 사건. 천재 탐정이 그림 속에 숨겨진 단서를 추적하며 범인의 정체를 밝혀낸다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F별을%20쫓는%20아이.png?alt=media&token=20480000-87e2-4363-9fc4-e115e81e94ca\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-805-946172-6\",\n" +
            "    \"title\": \"바람이 머무는 곳\",\n" +
            "    \"author\": \"오준혁\",\n" +
            "    \"summary\": \"시간을 되돌릴 수 있는 능력을 가진 소녀. 과거의 실수를 바로잡으려 하지만, 예상치 못한 결과들이 그녀를 기다린다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F바람이%20머무는%20곳.png?alt=media&token=a295fbd4-b118-4e0b-9fe5-076a1a59ce33\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-89-314-7603-3\",\n" +
            "    \"title\": \"정보처리기사 올인원\",\n" +
            "    \"author\": \"한소정\",\n" +
            "    \"summary\": \"한번에 합격, 자격증은 이기적 이렇게 기막힌 적중률\",\n" +
            "    \"status\": \"borrowed\",\n" +
            "    \"img\": \"book%2F9788931476033.jpg?alt=media&token=4b585c67-ba2e-4f6e-83a8-723fa74e4e2d\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"978-903-423255-9\",\n" +
            "    \"title\": \"시간의 흔적\",\n" +
            "    \"author\": \"이서연\",\n" +
            "    \"summary\": \"사막 한가운데서 발견된 고대 유적. 고고학 팀은 그곳에서 인류의 기원에 대한 충격적인 비밀을 마주하게 된다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F시간의%20흔적.png?alt=media&token=ecd98400-7b3c-4343-ab48-3e2de44ccdc8\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-01-1\",\n" +
            "    \"title\": \"별빛 속삭임\",\n" +
            "    \"author\": \"김은하\",\n" +
            "    \"summary\": \"밤하늘의 별빛 아래 펼쳐지는 감성 가득한 소녀의 성장 이야기입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F별빛%20속삭임.png?alt=media&token=ff49f861-a443-4857-be88-4ee4e6d3c8eb\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-02-8\",\n" +
            "    \"title\": \"시간의 정원사\",\n" +
            "    \"author\": \"박지훈\",\n" +
            "    \"summary\": \"시간을 가꾸는 정원이 존재한다면, 그 안에서 일어나는 기적 같은 이야기입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F시간의%20정원사.png?alt=media&token=bc2ca854-5662-4b37-ad5e-4049a32c1f3f\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-03-5\",\n" +
            "    \"title\": \"꿈을 엮는 마법\",\n" +
            "    \"author\": \"이수정\",\n" +
            "    \"summary\": \"현실과 꿈의 경계를 넘나드는 마법사가 펼치는 환상의 이야기입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F꿈을%20엮는%20마법.png?alt=media&token=3c399cd5-5a59-4abb-932f-2adb47eb7e22\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-05-9\",\n" +
            "    \"title\": \"잊혀진 숲의 비밀\",\n" +
            "    \"author\": \"정다솜\",\n" +
            "    \"summary\": \"잊혀진 숲 속에서 발견된 오래된 비밀과 신비로운 모험이 펼쳐집니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F잊혀진%20숲의%20비밀.png?alt=media&token=25243d40-b5e5-4e67-9d32-7cde3e88d5f0\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-06-6\",\n" +
            "    \"title\": \"푸른 달의 기사\",\n" +
            "    \"author\": \"윤태양\",\n" +
            "    \"summary\": \"푸른 달이 떠오르면 나타나는 전설의 기사가 세상을 구하는 영웅 서사입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F푸른%20달의%20기사.png?alt=media&token=448e9e0f-b289-4151-8ab6-4a7b869c073f\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-07-3\",\n" +
            "    \"title\": \"마음의 지도\",\n" +
            "    \"author\": \"한지민\",\n" +
            "    \"summary\": \"자신의 내면을 탐색하며 삶의 방향을 찾아가는 치유와 성장의 여정입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F마음의%20지도.png?alt=media&token=2e07f2b8-6978-4d61-b393-265101a32f80\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-08-0\",\n" +
            "    \"title\": \"마지막 페이지\",\n" +
            "    \"author\": \"강민준\",\n" +
            "    \"summary\": \"인생의 끝자락에서 발견한 진실과 감동을 그린 감성 소설입니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F마지막%20페이지.png?alt=media&token=470e96a1-f324-4492-8911-f771ac87deac\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-12345-10-3\",\n" +
            "    \"title\": \"물의 기억\",\n" +
            "    \"author\": \"임지호\",\n" +
            "    \"summary\": \"물속에 숨겨진 과거의 기억과 마주하며 진실을 찾아가는 미스터리 드라마입니다.\",\n" +
            "    \"status\": \"borrowed\",\n" +
            "    \"img\": \"book%2F물의%20기억.png?alt=media&token=1632bd28-0e72-4095-88b1-902b4f369b98\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-11-4567-890-1\",\n" +
            "    \"title\": \"꿈을 쫓는 구름 고래\",\n" +
            "    \"author\": \"제임스 리\",\n" +
            "    \"summary\": \"세상 끝자락, 하늘을 유영하는 거대한 구름 고래들이 사는 곳이 있습니다. 이 고래들은 사람들의 꿈을 먹고 자라며, 꿈이 사라지면 점점 작아져 사라져 버립니다. 작은 마을에 사는 소년 '피노'는 더 이상 꿈을 꾸지 않는 사람들과 사라져가는 구름 고래들을 보며 슬픔에 잠깁니다. 피노는 구름 고래들을 구하기 위해, 그리고 사람들에게 다시 꿈을 돌려주기 위해 전설 속 '꿈의 씨앗'을 찾아 떠나는 용감한 모험을 시작합니다. 그의 여정은 예상치 못한 친구들과 마법 같은 순간들로 가득합니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F꿈을%20쫓는%20구름%20고래.png?alt=media&token=68a3166b-eb4f-4331-8ec7-8b152562387a\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"isbn\": \"979-89-1234-567-8\",\n" +
            "    \"title\": \"꿈을 그리는 용의 소녀\",\n" +
            "    \"author\": \"김민지\",\n" +
            "    \"summary\": \"깊은 숲 속에 사는 수줍음 많은 소녀 '보라'는 그림을 통해 세상과 소통합니다. 어느 날, 보라는 밤하늘을 가르며 꿈을 먹고 사는 신비로운 분홍 용 '루나'를 만납니다. 루나는 사람들의 아름다운 꿈을 모아 하늘에 별처럼 흩뿌리는 특별한 능력을 가지고 있습니다. 하지만 악당 '그림자 도둑'이 나타나 사람들의 꿈을 훔쳐 가면서 세상은 점점 어두워지고, 루나도 힘을 잃어갑니다. 보라는 자신이 그린 그림들이 루나에게 힘을 줄 수 있다는 것을 깨닫고, 루나와 함께 그림자 도둑에 맞서 꿈을 되찾기 위한 아름다운 모험을 시작합니다.\",\n" +
            "    \"status\": \"available\",\n" +
            "    \"img\": \"book%2F꿈을%20그리는%20용의%20소녀.png?alt=media&token=25d7a174-909b-4200-89b6-3d5e7b13d8e6\"\n" +
            "  }\n" +
            "]";

    public String getBookList() {
        return bookList;
    }


    private String Products="[\n" +
            "  {\n" +
            "    \"id\": 1,\n" +
            "    \"name\": \"아메리카노\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 3500,\n" +
            "    \"img\": \"image%2Fcoffee1.png?alt=media&token=b753afe5-2550-4a88-8c32-0cf385089c62\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 2,\n" +
            "    \"name\": \"브라우니 아이스크림\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 4500,\n" +
            "    \"img\": \"image%2Fbrownie_with_icecream.png?alt=media&token=8db7fdb4-e780-4265-a3ca-4c7acc1228eb\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 3,\n" +
            "    \"name\": \"카페라떼\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 3500,\n" +
            "    \"img\": \"image%2Fcafe_latte.png?alt=media&token=306bff17-49ae-4834-b5d8-ac7e079af934\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 4,\n" +
            "    \"name\": \"카라멜 마키아토\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fcaramel_macchiato.png?alt=media&token=4e4728c8-6d97-4512-9f56-f1cc8a4ac95a\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 5,\n" +
            "    \"name\": \"치즈케이크\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fcheese_cake.png?alt=media&token=e2315d82-e995-4408-9300-8f91fa323db2\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 6,\n" +
            "    \"name\": \"초콜릿 케이크\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 5000,\n" +
            "    \"img\": \"image%2Fchocolate_cake.png?alt=media&token=d0556596-3b53-4668-b217-02633dc5bb71\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 7,\n" +
            "    \"name\": \"콜드브루\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 3500,\n" +
            "    \"img\": \"image%2Fcold_brew.png?alt=media&token=a6915ffb-64ac-4cea-ba30-42b54b252488\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 8,\n" +
            "    \"name\": \"크루아상\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 3000,\n" +
            "    \"img\": \"image%2Fcroissant.png?alt=media&token=550d1af8-2b6d-4403-bb2a-445d704e98c8\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 9,\n" +
            "    \"name\": \"얼그레이 티\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 3000,\n" +
            "    \"img\": \"image%2Fearl_grey_tea.png?alt=media&token=3eb81f87-b732-4f26-9717-94a0a69e1af4\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 10,\n" +
            "    \"name\": \"에스프레소\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 2500,\n" +
            "    \"img\": \"image%2Fespresso.png?alt=media&token=90a6a32a-3f50-4c07-89cc-19bd53652d89\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 11,\n" +
            "    \"name\": \"과일 타르트\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 4500,\n" +
            "    \"img\": \"image%2Ffruit_tart.png?alt=media&token=cd85201b-ceba-45d0-8787-498ab026d756\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 12,\n" +
            "    \"name\": \"자몽 에이드\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fgrapefruit_ade.png?alt=media&token=309b001f-9b0f-481e-b014-589f84aa11ec\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 13,\n" +
            "    \"name\": \"아이스 아메리카노\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 3000,\n" +
            "    \"img\": \"image%2Fice_americano.png?alt=media&token=38e2b29a-adcc-495f-a154-f1ae34c60d18\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 14,\n" +
            "    \"name\": \"아이스 녹차라떼\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fice_greentea_latte.png?alt=media&token=bbeb8fa8-62f2-41ad-bdb3-eacf74c01e28\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 15,\n" +
            "    \"name\": \"마카롱\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 2500,\n" +
            "    \"img\": \"image%2Fmacaroon.png?alt=media&token=7a82dbef-ad72-49ef-a18b-42d103c4636c\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 16,\n" +
            "    \"name\": \"망고 요거트 스무디\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4500,\n" +
            "    \"img\": \"image%2Fmango_yogurt_smoothie.png?alt=media&token=e71fde44-13a3-4f69-8df4-3411a932a254\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 17,\n" +
            "    \"name\": \"민트초코 프라페\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4800,\n" +
            "    \"img\": \"image%2Fmint_chocolate_frappe.png?alt=media&token=5b9f3284-4044-4d36-95b8-58f43ad70ec5\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 18,\n" +
            "    \"name\": \"레드벨벳 컵케이크\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fredvelvet_cupcake.png?alt=media&token=9ddd8afc-6875-46d6-9dc2-5a43f09cb745\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 19,\n" +
            "    \"name\": \"스콘\",\n" +
            "    \"type\": \"dessert\",\n" +
            "    \"price\": 3000,\n" +
            "    \"img\": \"image%2Fscone.png?alt=media&token=9bedf842-f6b0-47ee-b4e5-2626090fca6c\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 20,\n" +
            "    \"name\": \"딸기 스무디\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4500,\n" +
            "    \"img\": \"image%2Fstrawberry_smoothie.png?alt=media&token=c09fa8c6-ba71-4309-aab0-e45bda699bd5\"\n" +
            "  },\n" +
            "  {\n" +
            "    \"id\": 21,\n" +
            "    \"name\": \"바닐라 라떼\",\n" +
            "    \"type\": \"drink\",\n" +
            "    \"price\": 4000,\n" +
            "    \"img\": \"image%2Fvanilla_latte.png?alt=media&token=47686bc4-18ec-466d-a759-6deabee1774d\"\n" +
            "  }\n" +
            "]";


    public String getProducts() {
        return Products;
    }
}