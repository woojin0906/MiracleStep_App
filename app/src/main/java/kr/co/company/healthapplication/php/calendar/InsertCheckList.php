<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $listDate = isset($_POST["date"]) ? $_POST["date"] : "";
    $scheduleTitle = isset($_POST["scheduleTitle"]) ? $_POST["scheduleTitle"] : "";
    $checked = 0;

/* (3) DB안에 인덱스를 검색하는 문장. */
    $statement = mysqli_prepare($con, "SELECT listIndex FROM CheckList WHERE userId = ? AND listDate = ? ORDER BY listIndex DESC");
    mysqli_stmt_bind_param($statement, "ss", $userID, $listDate);
    mysqli_stmt_execute($statement);

/* select 인덱스 정보 가져오기 */
    mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $listIndex);

/* (4) DB안에 insert하는 문장. */
    $statement1 = mysqli_prepare($con, "INSERT INTO CheckList VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statement1, "sssss", $listDate, $userID, $listIndex, $checked, $scheduleTitle);
    mysqli_stmt_execute($statement1);

/* (5) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
