<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    $statement1 = mysqli_prepare($con, "SELECT UserID, UserName, UserPhoneNumber, UserImg FROM User WHERE UserID = ?");
    mysqli_stmt_bind_param($statement1, "s", $userID);
    mysqli_stmt_execute($statement1);

    mysqli_stmt_store_result($statement1);
    mysqli_stmt_bind_result($statement1, $UserID, $UserName, $UserPhoneNumber, $UserImg);

    $statement2 = mysqli_prepare($con, "SELECT UserHeight, UserWeight FROM UserInfo WHERE UserID = ? ORDER BY UserInfoDate ASC");
    mysqli_stmt_bind_param($statement2, "s", $userID);
    mysqli_stmt_execute($statement2);

    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $UserHeight, $UserWeight);

    $response = array();
    $response["success"] = false;

     while(mysqli_stmt_fetch($statement1) || mysqli_stmt_fetch($statement2) ){
        $response["success"] = true;
        $response["UserID"] = $UserID;
        $response["UserName"] = $UserName;
        $response["UserPhone"] = $UserPhoneNumber;
        $response["UserImg"] = $UserImg;

        $response["UserHeight"] = $UserHeight;
        $response["UserWeight"] = $UserWeight;
    }
    echo json_encode($response);
?>
