<?php
/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $listNum = isset($_POST["listNum"]) ? $_POST["listNum"] : 99999;
    $date = isset($_POST["date"]) ? $_POST["date"] : "";
    $title = isset($_POST["title"]) ? $_POST["title"] : "";

    $statement = mysqli_prepare($con, "UPDATE CheckList SET content = ? Where userId = ? AND  listDate = ? AND listIndex = ?");
    mysqli_stmt_bind_param($statement, "sssi", $title, $userID, $date, $listNum);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;
    $response["title"] = $title;
    $response["userID"] = $userID;
    $response["date"] = $date;
    $response["listNum"] = $listNum;

    echo json_encode($response);
?>
