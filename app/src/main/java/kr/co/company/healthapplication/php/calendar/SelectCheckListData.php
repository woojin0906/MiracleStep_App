<?php
/* (1) 서버 DB에 연결.*/
    $con = mysqli_connect("localhost", "miraclestep01", "", "miraclestep01"); // mysql 연결, IP, 사용자명, 비밀번호, 데이터베이스
    mysqli_query($con, 'SET NAMES utf8'); // 인코딩을 utf-8로 세팅. (한글 전송이 가능해짐.)

/* (2) 앱에서 선택한 카테고리를 가져옴. */
    //$cate = isset($_GET["category"]) ? $_GET["category"] : "TotalUserStep";

    // userID, nowDate

    $userID = isset($_POST["userID"]) ? $_POST["userID"] : "";
    $listDate = isset($_POST["date"]) ? $_POST["date"] : "";
    //$listDate=str_replace(".","-",$listDate);
    //$listDate=str_replace("/","-",$listDate);
    //$listDate = date('Ymd', strtotime($listDate));

/* (3) 현재 DB에 저장된 사용자들의 id와 카테고리의 값을 검색함. */
    $statement = mysqli_prepare($con, "SELECT listIndex, checked, content FROM CheckList WHERE userId = ? AND listDate = ?");
    mysqli_stmt_bind_param($statement, "ss", $userID, $listDate);
    mysqli_stmt_execute($statement);

// /* (4) DB안에 해당 카테고리가 일치하는 정보 가져오기 */
    mysqli_stmt_store_result($statement);
         mysqli_stmt_bind_result($statement, $listIndex, $checked, $content);

       $response = array();

    $allrevs = array();
    $response["success"] = false;

    while(mysqli_stmt_fetch($statement)) {

        $response["success"] = true;
        $response["listIndex"] = $listIndex;
        $response["checked"] = $checked;
        $response["content"] = $content;
        $allrevs[] = $response;
    }

/* (5) 로그인 실행 결과 전송. */
echo json_encode($allrevs);
?>