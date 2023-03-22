<?php
/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $runDate = isset($_POST["runDate"]) ? $_POST["runDate"] : "";
    $runTime = isset($_POST["runTime"]) ? $_POST["runTime"] : 0;
    $runDistance = isset($_POST["runDistance"]) ? $_POST["runDistance"] : 0.0;
    $runStep = isset($_POST["runStep"]) ? $_POST["runStep"] : 0;
    $runKcal = isset($_POST["runKcal"]) ? $_POST["runKcal"] : 0.0;

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "INSERT INTO RunRecord VALUES (?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "ssidid", $userID, $runDate, $runTime, $runDistance, $runStep, $runKcal);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;
    echo json_encode($runTime);
    echo json_encode($runDistance);
    echo json_encode($response);
?>
