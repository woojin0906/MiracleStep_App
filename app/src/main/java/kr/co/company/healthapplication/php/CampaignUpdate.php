<?php
 /*
      CampaignUpdate.php : 기업의 캠페인 수정을 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $titleName = isset($_POST["titleName"]) ? $_POST["titleName"] : "";
    $startdate = isset($_POST["startdate"]) ? $_POST["startdate"] : "";
    $date = isset($_POST["date"]) ? $_POST["date"] : "";
    $content = isset($_POST["content"]) ? $_POST["content"] : "";
    $maxStep = isset($_POST["maxStep"]) ? $_POST["maxStep"] : "";
    $campaignIndex = isset($_POST["dNum"]) ? $_POST["dNum"] : "";

/* (3) DB안에 insert하는 문장. (User) */
    $statement = mysqli_prepare($con, "UPDATE CampaignList SET title = ?, startDate = ?, lastDate = ?, maxDonation = ?, content = ? Where campaignIndex = ?");
    mysqli_stmt_bind_param($statement, "sssisi", $titleName, $startdate, $date, $maxStep, $content, $campaignIndex);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
