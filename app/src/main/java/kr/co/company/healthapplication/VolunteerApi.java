//package kr.co.company.healthapplication;
//
//import org.xmlpull.v1.XmlPullParser;
//import org.xmlpull.v1.XmlPullParserFactory;
//
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.util.ArrayList;
//
//public class VolunteerApi {
//
//    private static String ServiceKey = "9yPpiG%2BRuqf2HZdGejBfaq3OtNl5MITxR3hqJv5nShg66gl1nJaVjepPF4KIUf169zuRKYnlhS5omW7xmXVb6w%3D%3D";
//
//    public VolunteerApi() {
//        try {
//            apiParserSearch();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public ArrayList<Volunteer> apiParserSearch() throws Exception {
//        URL url = new URL(getURLParam(null));
//
//        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
//        factory.setNamespaceAware(true);
//        XmlPullParser xpp= factory.newPullParser();
//        BufferedInputStream bis = new BufferedInputStream(url.openStream());
//        xpp.setInput(bis, "utf-8");
//
//        String tag = null;
//        int event_type = xpp.getEventType();
//
//        ArrayList<Volunteer> volunteer = new ArrayList<Volunteer>();
//
//        String titleName = null, groupName=null, category=null, state=null, company=null, recuDate=null, personCategory=null, volunCount=null, day=null, time=null, place=null, volunDate=null, content=null, managerName=null, managerPhone=null, managerAddress=null;
//        boolean btitleName=false, bgroupName=false, bcategory=false, bstate=false, bcompany=false, brecuDate=false, bpersonCategory=false, bvolunCount=false, bday=false, btime=false, bplace=false, bvolunDate=false, bcontent=false, bmanagerName=false, bmanagerPhone=false, bmanagerAddress=false;
//
//        while (event_type != XmlPullParser.END_DOCUMENT) {
//            if(event_type == XmlPullParser.START_TAG) {
//                tag = xpp.getName();
//                if(tag.equals("progrmSj")) {
//                    btitleName=true;
//                }
//                if(tag.equals("mnnstNm")) {
//                    bgroupName=true;
//                }
//                if(tag.equals("srvcClCode")) {
//                    bcategory=true;
//                }
//                if(tag.equals("progrmSttusSe")){
//                    bstate=true;
//                }
//                if(tag.equals("nanmmbyNm")) {
//                    bcompany=true;
//                }
//                if(tag.equals("progrmEndde")) {
//                    brecuDate=true;
//                }
//                if(tag.equals("adultPosblAt")) {
//                    bpersonCategory=true;
//                }
//                if(tag.equals("rcritNmpr")) {
//                    bvolunCount=true;
//                }
//                if(tag.equals("actWkdy")) {
//                    bday=true;
//                }
//                if(tag.equals("actBeginTm")) {
//                    btime=true;
//                }
//                if(tag.equals("actPlace")) {
//                    bplace=true;
//                }
//                if(tag.equals("progrmBgnde")) {
//                    bvolunDate=true;
//                }
//                if(tag.equals("progrmCn")) {
//                    bcontent=true;
//                }
//                if(tag.equals("nanmmbyNmAdmn")) {
//                    bmanagerName=true;
//                }
//                if(tag.equals("telno")) {
//                    bmanagerPhone=true;
//                }
//                if(tag.equals("postAdres")) {
//                    bmanagerAddress=true;
//                }
//
//            } else if (event_type == XmlPullParser.TEXT) {
//                if(btitleName == true) {
//                    titleName = xpp.getText();
//                    btitleName = false;
//                } else if(bgroupName == true) {
//                    groupName = xpp.getText();
//                    bgroupName = false;
//                } else if(bcategory == true) {
//                    category = xpp.getText();
//                    bcategory = false;
//                }
//                else if(bstate == true) {
//                    state = xpp.getText();
//                    bstate = false;
//                }
//                else if(bcompany == true) {
//                    company = xpp.getText();
//                    bcompany = false;
//                }
//                else if(bcompany == true) {
//                    company = xpp.getText();
//                    bcompany = false;
//                }
//                else if(brecuDate == true) {
//                    recuDate = xpp.getText();
//                    brecuDate = false;
//                }
//                else if(bpersonCategory == true) {
//                    personCategory = xpp.getText();
//                    bpersonCategory = false;
//                }
//                else if(bvolunCount == true) {
//                    volunCount = xpp.getText();
//                    bvolunCount = false;
//                }
//                else if(bday == true) {
//                    day = xpp.getText();
//                    bday = false;
//                }
//                else if(btime == true) {
//                    time = xpp.getText();
//                    btime = false;
//                }
//                else if(bplace == true) {
//                    place = xpp.getText();
//                    bplace = false;
//                }
//                else if(bvolunDate == true) {
//                    volunDate = xpp.getText();
//                    bvolunDate = false;
//                }
//                else if(bcontent == true) {
//                    content = xpp.getText();
//                    bcontent = false;
//                }
//                else if(bmanagerName == true) {
//                    managerName = xpp.getText();
//                    bmanagerName = false;
//                }
//                else if(bmanagerPhone == true) {
//                    managerPhone = xpp.getText();
//                    bmanagerPhone = false;
//                }
//                else if(bmanagerAddress== true) {
//                    managerAddress = xpp.getText();
//                    bmanagerAddress = false;
//                }
//
//            } else if (event_type == XmlPullParser.END_TAG) {
//                tag = xpp.getName();
//                if(tag.equals("row")) {
//                    Volunteer entity = new Volunteer();
//                    entity.setTitleName(titleName);
//                    entity.setGroupName(groupName);
//                    entity.setCategory(category);
//                    entity.setState(state);
//                    entity.setCompany(company);
//                    entity.setRecuDate(recuDate);
//                    entity.setPersonCategory(personCategory);
//                    entity.setVolunCount(Integer.parseInt(volunCount));
//                    entity.setDay(day);
//                    entity.setTime(time);
//                    entity.setPlace(place);
//                    entity.setVolunDate(volunDate);
//                    entity.setContent(content);
//                    entity.setManagerName(managerName);
//                    entity.setManagerPhone(managerPhone);
//                    entity.setManagerAddress(managerAddress);
//                    volunteer.add(entity);
//                    System.out.println(volunteer.size());
//                }
//            } event_type = xpp.next();
//        } System.out.println(volunteer.size());
//        return  volunteer;
//
//    }
//
//    private  String getURLParam(String search) {
//        // 공공데이터 API 주소, 키
//        String url = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem=" + ServiceKey + "&Type=xml&plndex=1&pSize=300";
//        return  url;
//    }
//
//    public static void main(String[] args) {
//        new VolunteerApi();
//    }
//    String getXmlData() {
//        StringBuffer buffer=new StringBuffer();
//        String str= spinner.getSelectedItem().toString();  //spinner에 선택된 Text얻어오기
//
//        String queryUrl="http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrPartcptnItem?=9yPpiG%2BRuqf2HZdGejBfaq3OtNl5MITxR3hqJv5nShg66gl1nJaVjepPF4KIUf169zuRKYnlhS5omW7xmXVb6w%3D%3D" +"&srvcClCod=" + str;
//
//        try{
//            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
//            InputStream is= url.openStream(); //url위치로 입력스트림 연결
//
//            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
//            XmlPullParser xpp= factory.newPullParser();
//            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기
//
//            String tag;
//
//            xpp.next();
//            int eventType= xpp.getEventType();
//
//            while( eventType != XmlPullParser.END_DOCUMENT ){
//                switch( eventType ){
//                    case XmlPullParser.START_DOCUMENT:
//                        buffer.append("파싱 시작...\n\n");
//                        break;
//
//                    case XmlPullParser.START_TAG:
//                        tag= xpp.getName();//태그 이름 얻어오기
//
//                        if(tag.equals("item")) ;// 첫번째 검색결과
//                        else if(tag.equals("progrmSj")){
//                            buffer.append("봉사제목 : ");
//                            xpp.next();
//                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("mnnstNm")){
//                            buffer.append("모집기관 : ");
//                            xpp.next();
//                            buffer.append(xpp.getText());//category 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("srvcClCode")){
//                            buffer.append("봉사분야 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("progrmSttusSe")){
//                            buffer.append("모집상태 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//telephone 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("nanmmbyNm")){
//                            buffer.append("등록기관 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n");//줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("progrmEndde")){
//                            buffer.append("봉사종료일자 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("adultPosblAt")){
//                            buffer.append("봉사자유형 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("rcritNmpr")){
//                            buffer.append("모집인원 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("actWkdy")){
//                            buffer.append("활동요일 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("actBeginTm")){
//                            buffer.append("봉사시간 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("actPlace")){
//                            buffer.append("봉사장소 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("progrmBgnde")){
//                            buffer.append("봉사기간 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("progrmCn")){
//                            buffer.append("내용 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("nanmmbyNmAdmn")){
//                            buffer.append("담당자명 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("telno")){
//                            buffer.append("전화번호 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        else if(tag.equals("postAdres")){
//                            buffer.append("담당자 주소 :");
//                            xpp.next();
//                            buffer.append(xpp.getText());//mapy 요소의 TEXT 읽어와서 문자열버퍼에 추가
//                            buffer.append("\n"); //줄바꿈 문자 추가
//                        }
//                        break;
//
//                    case XmlPullParser.TEXT:
//                        break;
//
//                    case XmlPullParser.END_TAG:
//                        tag= xpp.getName(); //테그 이름 얻어오기
//
//                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
//                        break;
//                }
//
//                eventType= xpp.next();
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        buffer.append("파싱 끝\n");
//        return buffer.toString();//StringBuffer 문자열 객체 반환
//
//
//
//    }
//}
