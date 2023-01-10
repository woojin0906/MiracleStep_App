<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $runDate = isset($_POST["runDate"]) ? $_POST["runDate"] : "";

    $statement = mysqli_prepare($con, "SELECT * FROM Running WHERE UserID = ? AND RunDate = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $runDate);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $runDate, $runTime, $runDistance, $runStep, $runKcal);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["runDate"] = $runDate;
        $response["runTime"] = $runTime;
        $response["runDistance"] = $runDistance;
        $response["runStep"] = $runStep;
        $response["runKcal"] = $runKcal;
    }
    echo json_encode($response);
?>
