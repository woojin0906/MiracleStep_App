<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $id = isset($_POST["id"]) ? $_POST["id"] : "";

    // 사용자 정보
    $statement = mysqli_prepare($con, "SELECT id, name, proposer, joinDate FROM Organization WHERE id = ?;");
    mysqli_stmt_bind_param($statement, "s", $id);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $name, $proposer, $joinDate);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;

        $response["id"] = $id;
        $response["name"] = $name;
        $response["proposer"] = $proposer;
        $response["joinDate"] = $joinDate;

    }
    echo json_encode($response);
?>