<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    // 사용자 정보
    $statement1 = mysqli_prepare($con, "SELECT id, name, birth, height, weight, availableStep FROM User WHERE id = ? ");
    mysqli_stmt_bind_param($statement1, "s", $userID);
    mysqli_stmt_execute($statement1);

    mysqli_stmt_store_result($statement1);
    mysqli_stmt_bind_result($statement1, $id, $name, $birth, $height, $weight, $availableStep);

    // 총 기부
    $statement2 = mysqli_prepare($con, "SELECT sum(donationStep) FROM DonationHistory WHERE userId = ? ");
    mysqli_stmt_bind_param($statement2, "s", $userID);
    mysqli_stmt_execute($statement2);

    mysqli_stmt_store_result($statement2);
    mysqli_stmt_bind_result($statement2, $totalDonationStep);


    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement1) || mysqli_stmt_fetch($statement2)) {
        $response["success"] = true;
        $response["id"] = $id;
        $response["name"] = $name;
        $response["birth"] = $birth;
        $response["height"] = $height;
        $response["weight"] = $weight;
        $response["availableStep"] = $availableStep;

        $response["totalDonationStep"] = $totalDonationStep;
    }
    echo json_encode($response);
?>