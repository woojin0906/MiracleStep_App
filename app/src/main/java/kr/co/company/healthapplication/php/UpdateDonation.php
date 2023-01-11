<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $dNum = isset($_POST["dNum"]) ? $_POST["dNum"] : "0";

    $updateStep = isset($_POST["updateStep"]) ? $_POST["updateStep"] : "";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "UPDATE Donation SET NowStep = ? Where DNum = ?");
    mysqli_stmt_bind_param($statement, "ii", $updateStep, $dNum);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
