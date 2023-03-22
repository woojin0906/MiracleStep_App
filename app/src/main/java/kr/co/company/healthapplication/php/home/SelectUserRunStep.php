<?php
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $runDate = isset($_POST["nowDate"]) ? $_POST["nowDate"] : "" ;
    $runStep = 0;

    $statement1 = mysqli_prepare($con, "SELECT runStep FROM RunRecord WHERE userID = ? AND runDate = ?");
    mysqli_stmt_bind_param($statement1, "ss", $userID, $runDate);
    mysqli_stmt_execute($statement1);
    mysqli_stmt_store_result($statement1);
    mysqli_stmt_bind_result($statement1, $runStep);

    if(!mysqli_stmt_fetch($statement1)) {
        $intZero = 0;
        $stringZero = "0";
        $doubleZero = 0.0;
        $runStep = 0;
        $statement2 = mysqli_prepare($con, "INSERT INTO RunRecord VALUES (?,?,?,?,?,?)");
        mysqli_stmt_bind_param($statement2, "sssdid", $userID, $runDate, $stringZero, $doubleZero, $intZero, $doubleZero);
        mysqli_stmt_execute($statement2);
    }

    $response = array();
    $response["success"] = true;
    $response["userID"] = $userID;
    $response["runDate"] = $runDate;
    $response["runStep"] = $runStep;
    echo json_encode($response);
?>
