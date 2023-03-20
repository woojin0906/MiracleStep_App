<?php
    /*
      Register.php : 회원가입을 처리하는 php
      $ : 변수를 선언해주는 기호
      con : mysql 연결을 시도하는 변수.
    */

/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "tndnqja11!!", "miraclestep01");
    mysqli_query($con, 'SET NAMES utf8');   /* 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.) */

/* (2) DB에 저장할 객체 선언. */
    $id = isset($_POST["id"]) ? $_POST["id"] : "";
    $pw = isset($_POST["pw"]) ? $_POST["pw"] : "";
    $name = isset($_POST["name"]) ? $_POST["name"] : "";
    $phoneNumber = isset($_POST["phoneNumber"]) ? $_POST["phoneNumber"] : "";
    $birth = isset($_POST["birth"]) ? $_POST["birth"] : "";

    (int)$height = isset($_POST["height"]) ? $_POST["height"] : "";
    (int)$weight = isset($_POST["weight"]) ? $_POST["weight"] : "";
    (int)$availableStep = 0;

/* (3) DB안에 insert하는 문장. (User, UserInfo) */
    $statement = mysqli_prepare($con, "INSERT INTO User VALUES (?,?,?,?,?,?,?,?)");
    mysqli_stmt_bind_param($statement, "sssssiii", $id, $pw, $name, $phoneNumber, $birth, $height, $weight, $availableStep);
    mysqli_stmt_execute($statement);

/* (4) 성공 여부 전송. */
    $response = array();
    $response["success"] = true;
//     $response["id"] = $id;
//     $response["pw"] = $pw;
//     $response["name"] = $name;
//     $response["phoneNumber"] = $phoneNumber;
//     $response["birth"] = $birth;
//     $response["height"] = $height;
//     $response["weight"] = $weight;
//     $response["availableStep"] = $availableStep;

    echo json_encode($response);

?>
