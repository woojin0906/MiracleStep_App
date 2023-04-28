<?php
 /*
      CampaignInsert.php : 캠페인을 등록하는 사용자 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
  $category = isset($_POST["category"]) ? $_POST["category"] : "";
  $titleName = isset($_POST["titleName"]) ? $_POST["titleName"] : "";
  $name = isset($_POST["name"]) ? $_POST["name"] : "a";
  $startdate = isset($_POST["startdate"]) ? $_POST["startdate"] : "";
  $date = isset($_POST["date"]) ? $_POST["date"] : "";
  $maxStep = isset($_POST["maxStep"]) ? $_POST["maxStep"] : "";
  $content = isset($_POST["content"]) ? $_POST["content"] : "";
  $nowStep = 0;
  $userImg = "img";

/* (3) DB안에 insert하는 문장. (UserDonation) */
    $statement = mysqli_prepare($con, "INSERT INTO CampaignList(category, hostingGroup, title, content, lastDate, startDate, maxDonation, contentImage, nowDonation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
    mysqli_stmt_bind_param($statement, "ssssssisi", $category, $name, $titleName, $startdate, $date, $maxStep, $content, $userImg, $nowStep);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

/* (5) 실행 결과 전송. */
    echo json_encode($response);
?>
