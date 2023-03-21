<?php
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

    $userId = isset($_POST["userId"]) ? $_POST["userId"] : "";
    $userHeight = (int)isset($_POST["userHeight"]) ? $_POST["userHeight"] : "";
    $userWeight = (int)isset($_POST["userWeight"]) ? $_POST["userWeight"] : "";

    $statement1 = mysqli_prepare($con, "UPDATE User SET height = ?, weight = ? Where id = ?");
    mysqli_stmt_bind_param($statement1, "iis", $userHeight, $userWeight, $userId);
    mysqli_stmt_execute($statement1);

/*  성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);
?>