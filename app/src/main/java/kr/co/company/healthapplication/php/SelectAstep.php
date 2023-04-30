<?php

// 홈화면 기부 가능 걸음 조회

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $id = isset($_POST["id"]) ? $_POST["id"] : "";

    // 사용자 정보
    $statement = mysqli_prepare($con, "SELECT availableStep FROM User WHERE id = ?;");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $availableStep);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["aStep"] = $availableStep;
    }

    echo json_encode($response);
?>