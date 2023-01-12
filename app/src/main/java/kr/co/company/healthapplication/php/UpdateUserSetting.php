<?php
/*2023-01-12 이수*/
/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep", "비밀번호", "miraclestep");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $returnUserID = isset($_POST["returnUserID"]) ? $_POST["returnUserID"] : "";
    $userImg = isset($_POST["userImg"]) ? $_POST["userImg"] : "";
    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $userName = isset($_POST["userName"]) ? $_POST["userName"] : "";
    $userPhone = isset($_POST["userPhone"]) ? $_POST["userPhone"] : "";
    $userHeight = isset($_POST["userHeight"]) ? $_POST["userHeight"] : "";
    $userWeight = isset($_POST["userWeight"]) ? $_POST["userWeight"] : "";
    $userDate = isset($_POST["userDate"]) ? $_POST["userDate"] : "";

    $statement1 = mysqli_prepare($con, "UPDATE User SET UserImg = ?,UserID = ?, UserName = ?, UserPhoneNumber = ? Where UserID = ?");
    mysqli_stmt_bind_param($statement1, "sssss", $userImg, $userID, $userName, $userPhone, $returnUserID);
    mysqli_stmt_execute($statement1);

    $statement2 = mysqli_prepare($con, "INSERT INTO UserInfo VALUES (?,?,?,?)");
    mysqli_stmt_bind_param($statement2, "sdds", $userID, $userDate, $userHeight, $userWeight);
    mysqli_stmt_execute($statement2);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>
