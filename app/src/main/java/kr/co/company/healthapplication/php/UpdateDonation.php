<?php
 /*
      UpdateDonation.php : 해당 캠페인의 현재 기부된 걸음 수를 처리하는 php (POST 형식으로 MySQL로부터 데이터를 받아옴.)
      con : mysql 연결을 시도하는 변수.
      "$변수" 로 변수 선언
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "비밀번호", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $dNum = isset($_POST["dNum"]) ? $_POST["dNum"] : "0";

    $updateStep = isset($_POST["updateStep"]) ? $_POST["updateStep"] : "";

/* (3) DB안에 insert하는 문장. (Donation) */
    $statement = mysqli_prepare($con, "UPDATE CampaignList SET nowDonation = ? Where campaignIndex = ?");
    mysqli_stmt_bind_param($statement, "ii", $updateStep, $dNum);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
