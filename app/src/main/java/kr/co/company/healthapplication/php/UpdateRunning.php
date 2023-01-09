<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "비밀번호", "tndnqja1!", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    // date타입을 php는 String으로 받아들이기 때문에 형변환이 필요해요... (2023-01-03 이수)
    $runDate = isset($_POST["runDate"]) ? $_POST["runDate"] : "";
    //$runDate = str_replace(".","-",$runDate);
    //$runDate = str_replace("/","-",$runDate);
    //$runDate = date('Ymd', strtotime($runDate));

    $runTime = isset($_POST["runTime"]) ? $_POST["runTime"] : "";
    $runDistance = isset($_POST["runDistance"]) ? $_POST["runDistance"] : "";
    $runStep = isset($_POST["runStep"]) ? $_POST["runStep"] : "";
    $runKcal = isset($_POST["runKcal"]) ? $_POST["runKcal"] : "";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "UPDATE Running SET RunTime = ?, RunDistance = ?, RunStep = ?, RunKcal = ? Where UserID = ? and RunDate = ?);
    mysqli_stmt_bind_param($statement, "s", $runTime, $runDistance, $runStep, $runKcal, $userID, $runDate);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
