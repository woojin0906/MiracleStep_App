<?php
    /*
      OrganizJoin.php : 기업 회원가입을 처리하는 php
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $oId = isset($_POST["id"]) ? $_POST["id"] : "";
    $oPw = isset($_POST["pw"]) ? $_POST["pw"] : "";
    $oName = isset($_POST["name"]) ? $_POST["name"] : "";
    $oProposer = isset($_POST["proposer"]) ? $_POST["proposer"] : "";
    $joinDate = isset($_POST["joinDate"]) ? $_POST["joinDate"] : "";

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "INSERT INTO Organization(id, pw, name, proposer, joinDate) VALUES(?, ?, ?, ?, ?);");
    mysqli_stmt_bind_param($statement, "sssss", $oId, $oPw, $oName, $oProposer, $joinDate);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;

    echo json_encode($response);

?>
