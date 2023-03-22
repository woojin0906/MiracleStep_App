<?php

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $sDate = isset($_POST["nowDate"]) ? $_POST["nowDate"] : "";

    $statement = mysqli_prepare($con, "SELECT listIndex, checked, content FROM CheckList WHERE userId = ? AND listDate = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $sDate);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
     mysqli_stmt_bind_result($statement, $ListNum, $Checked, $CheckContent);

   $response = array();

    $allrevs = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {
        $response["success"] = true;
        $response["ListNum"] = $ListNum;
        $response["Checked"] = $Checked;
        $response["CheckContent"] = $CheckContent;
        $allrevs[] = $response;
    }

    echo json_encode($allrevs);
?>