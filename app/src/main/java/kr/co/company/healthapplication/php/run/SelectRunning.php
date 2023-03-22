<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $runDate = isset($_POST["runDate"]) ? $_POST["runDate"] : "";

    $statement = mysqli_prepare($con, "SELECT * FROM RunRecord WHERE userId = ? AND runDate = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $runDate);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $userID, $runDate, $runTime, $runDistance, $runStep, $runKcal);

    $response = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["userID"] = $userID;
        $response["runDate"] = $runDate;
        $response["runTime"] = $runTime;
        $response["runDistance"] = $runDistance;
        $response["runStep"] = $runStep;
        $response["runKcal"] = $runKcal;
    }
    echo json_encode($response);
?>
