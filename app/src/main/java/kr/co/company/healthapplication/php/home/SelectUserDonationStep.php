<?php
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";

    $statement = mysqli_prepare($con, "SELECT donationStep FROM DonationHistory WHERE userID = ?");
    mysqli_stmt_bind_param($statement, "s", $userID);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $donationStep);

    $totalDonationStep = 0;
    while(mysqli_stmt_fetch($statement)) {
        $totalDonationStep += $donationStep;
    }

    $response = array();
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["totalDonationStep"] = $totalDonationStep;
    echo json_encode($response);
?>
