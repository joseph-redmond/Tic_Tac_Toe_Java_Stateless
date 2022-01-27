package com.example.tictactoe.Board;

import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardService {

    // Entry Point
    public String placePieceForPerfectMove(char[][] inputMatrix) {
        if(inputMatrix.length != 3 || inputMatrix[0].length != 3) return convertMatrixToString(inputMatrix);
        char[][] perfectlyPlayedGameBoard = placePieceIfWon(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix,perfectlyPlayedGameBoard)){
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfNeedToBlock(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)){
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanFork(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)) {
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanBlockFork(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)){
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanInCenter(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)){
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanInOppositeCorner(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)) {
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanInCorner(inputMatrix);
        if(!checkIfMatrixIsEqual(inputMatrix, perfectlyPlayedGameBoard)) {
            return convertMatrixToString(perfectlyPlayedGameBoard);
        }
        perfectlyPlayedGameBoard = placePieceIfCanInCenterOfSide(inputMatrix);
        return convertMatrixToString(perfectlyPlayedGameBoard);
    }
    public int[] ifWonReturnIndex(char[][] inputMatrix) {
        int[] returnValue = indexIfCanWinHorizontally(inputMatrix, 'o', 'x');
        if(returnValue[0] == -1 || returnValue[1] == -1) {
            returnValue = indexIfCanWinVertically(inputMatrix, 'o', 'x');
            if(returnValue[0] == -1 || returnValue[1] == -1){
                returnValue = indexIfCanWinDiagonally(inputMatrix, 'o', 'x');
            }
        }
        return returnValue;
    }
    // Start of solving algorithm Functions
    private char[][] placePieceIfWon(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = ifWonReturnIndex(inputMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfNeedToBlock(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfNeedToBlock(clonedMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanFork(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanFork(clonedMatrix, 'o', 'x');
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanBlockFork(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanFork(clonedMatrix, 'x', 'o');
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanInCenter(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanPlaceInCenter(clonedMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanInOppositeCorner(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanPlaceInOppositeCorner(clonedMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanInCorner(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanPlaceInCorner(clonedMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }
    private char[][] placePieceIfCanInCenterOfSide(char[][] inputMatrix) {
        char[][] clonedMatrix = cloneMatrix(inputMatrix);
        int[] possiblePieceToPlace = indexIfCanPlaceInCenterOfSide(clonedMatrix);
        if(hasValidIndex(possiblePieceToPlace)){
            clonedMatrix[possiblePieceToPlace[0]][possiblePieceToPlace[1]] = 'o';
        }
        return clonedMatrix;
    }

    // Individual implementation and pulling functions
    private int[] indexIfCanWinHorizontally(char [][] inputMatrix, char playerPiece, char enemyPiece) {
        int oCount = 0;
        int[] possibleWinSpace = new int[] {-1, -1};
        for(int i = 0; i < inputMatrix.length; i++){
            if(oCount == 2 && possibleWinSpace[0] != -1 && possibleWinSpace[1] != -1) {
                return possibleWinSpace;
            }
            for(int j = 0; j < inputMatrix[i].length; j++){
                if(inputMatrix[i][j] == ' ' && possibleWinSpace[0] == -1 && possibleWinSpace[1] == -1){
                    possibleWinSpace[0] = i;
                    possibleWinSpace[1] = j;
                    continue;
                }

                if(inputMatrix[i][j] == ' ' || inputMatrix[i][j] == enemyPiece){
                    oCount = 0;
                    possibleWinSpace[0] = -1;
                    possibleWinSpace[1] = -1;
                    break;
                }

                if(inputMatrix[i][j] == playerPiece) {
                    oCount++;
                }
            }
        }

        if(oCount == 2 && possibleWinSpace[0] != -1 && possibleWinSpace[1] != -1) {
            return possibleWinSpace;
        } else {
            return new int[]{-1, -1};
        }
    }
    private int[] indexIfCanWinVertically(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int oCount = 0;
        int[] possibleWinSpace = new int[] {-1, -1};
        for(int i = 0; i < inputMatrix[0].length; i++){
            if(oCount == 2 && possibleWinSpace[0] != -1 && possibleWinSpace[1] != -1){
                return possibleWinSpace;
            }
            for(int j = 0; j < inputMatrix.length; j++){
                if(inputMatrix[j][i] == ' ' && possibleWinSpace[0] == -1 && possibleWinSpace[1] == -1){
                    possibleWinSpace[0] = j;
                    possibleWinSpace[1] = i;
                    continue;
                }

                if(inputMatrix[j][i] == ' ' || inputMatrix[j][i] == enemyPiece){
                    oCount = 0;
                    possibleWinSpace[0] = -1;
                    possibleWinSpace[1] = -1;
                    break;
                }

                if(inputMatrix[j][i] == playerPiece) {
                    oCount++;
                }
            }
        }

        if(oCount == 2 && possibleWinSpace[0] != -1 && possibleWinSpace[1] != -1) {
            return possibleWinSpace;
        } else {
            return new int[]{-1, -1};
        }
    }
    private int[] indexIfCanWinDiagonally(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int[] maybeWonLeftDiag = indexIfWonLeftDiagonal(inputMatrix, playerPiece, enemyPiece);
        if(maybeWonLeftDiag[0] != -1 && maybeWonLeftDiag[1] != -1) {
            return maybeWonLeftDiag;
        }
        int[] maybeWonRightDiag = indexIfWonRightDiagonal(inputMatrix, playerPiece, enemyPiece);
        if(maybeWonRightDiag[0] != -1 && maybeWonRightDiag[1] != -1){
            return maybeWonRightDiag;
        }
        return new int[] {-1, -1};
    }
    private int[] indexIfWonLeftDiagonal(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int oCount = 0;
        int[] plossibleWinIndex = new int[] {-1, -1};
        for(int i = 0; i < inputMatrix.length; i++){
            if(inputMatrix[i][i] == enemyPiece){
                plossibleWinIndex[0] = -1;
                plossibleWinIndex[1] = -1;
                break;
            }
            if(inputMatrix[i][i] == ' ') {
                plossibleWinIndex[0] = i;
                plossibleWinIndex[1] = i;
                continue;
            }
            if(inputMatrix[i][i] == playerPiece) {
                oCount++;
            }
        }
        if(oCount == 2 && plossibleWinIndex[0] != -1 && plossibleWinIndex[1] != -1) {
            return plossibleWinIndex;
        }
        else {
            return new int[] {-1, -1};
        }
    }
    private int[] indexIfWonRightDiagonal(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int oCount = 0;
        int[] plausibleWinSpace = new int[] {-1, -1};
        int increasingCount = 0;
        int decreasingCount = inputMatrix.length - 1;
        for(int i = 0; i < inputMatrix.length; i++) {
            if(inputMatrix[decreasingCount][increasingCount] == enemyPiece) {
                plausibleWinSpace[0] = -1;
                plausibleWinSpace[1] = -1;
                break;
            }

            if(inputMatrix[decreasingCount][increasingCount] == ' '){
                plausibleWinSpace[0] = decreasingCount;
                plausibleWinSpace[1] = increasingCount;
                increasingCount++;
                decreasingCount--;
                continue;
            }

            if(inputMatrix[decreasingCount][increasingCount] == playerPiece){
                oCount++;
                increasingCount++;
                decreasingCount--;
            }
        }

        if(oCount == 2 && plausibleWinSpace[0] != -1) {
            return plausibleWinSpace;
        } else {
            return new int[] {-1, -1};
        }
    }
    private int[] indexIfNeedToBlock(char[][] inputMatrix) {

        int[] possibleBlockIndex = indexIfCanWinHorizontally(inputMatrix, 'x', 'o');
        if(possibleBlockIndex[0] != -1 && possibleBlockIndex[1] != -1) {
            return possibleBlockIndex;
        }
        possibleBlockIndex = indexIfCanWinVertically(inputMatrix, 'x', 'o');
        if(possibleBlockIndex[0] != -1 && possibleBlockIndex[1] != -1) {
            return possibleBlockIndex;
        }
        possibleBlockIndex = indexIfCanWinDiagonally(inputMatrix, 'x', 'o');
        return possibleBlockIndex;
    }
    private int[] indexIfCanFork(char[][] inputMatrix, char playerPiece, char enemyPiece) {


        int[] openHorizontal = indexIfHorizontalOpen(inputMatrix, playerPiece, enemyPiece);
        int[] openVertical = indexIfVerticalOpen(inputMatrix, playerPiece, enemyPiece);
        int horizontalValue = openHorizontal[1];
        int verticalValue = openVertical[0];
        if(openHorizontal[0] == -1 && openHorizontal[1] == -1 || openVertical[0] == -1 && openVertical[1] == -1) {
            return new int[] {-1, -1};
        }
        if(inputMatrix[verticalValue][horizontalValue] != ' '){
            horizontalValue = openHorizontal[0];
            verticalValue = openVertical[1];
        }
        if(inputMatrix[horizontalValue][verticalValue] != ' '){
            return new int[] {-1, -1};
        }
        int[] interceptPoint = new int[] {horizontalValue, verticalValue};

        return interceptPoint;
    }
    private int[] indexIfHorizontalOpen(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int spaceCount = 0;
        int[] playerPieceIndex = new int[] {-1, -1};
        for(int i = 0; i < inputMatrix.length; i++) {
            if(spaceCount == 2 && playerPieceIndex[0] != -1) return playerPieceIndex;
            for(int j = 0; j < inputMatrix[i].length; j++) {
                if(inputMatrix[i][j] == ' ') {
                    spaceCount++;
                    continue;
                }

                if(inputMatrix[i][j] == enemyPiece){
                    spaceCount = 0;
                    playerPieceIndex[0] = -1;
                    playerPieceIndex[1] = -1;
                    break;
                }

                if(inputMatrix[i][j] == playerPiece) {
                    playerPieceIndex[0] = i;
                    playerPieceIndex[1] = j;
                }
            }
        }
        if(spaceCount == 2 && playerPieceIndex[0] != -1 && playerPieceIndex[1] != -1) return playerPieceIndex;
        return new int[] {-1, -1};
    }
    private int[] indexIfVerticalOpen(char[][] inputMatrix, char playerPiece, char enemyPiece) {
        int spaceCount = 0;
        int[] playerPieceIndex = new int[] {-1, -1};
        for(int i = 0; i < inputMatrix[0].length; i++) {
            if(spaceCount == 2 && playerPieceIndex[0] != -1) return playerPieceIndex;
            for(int j = 0; j < inputMatrix[i].length; j++){
                if(inputMatrix[j][i] == ' ') {
                    spaceCount++;
                    continue;
                }

                if(inputMatrix[j][i] == enemyPiece){
                    spaceCount = 0;
                    playerPieceIndex[0] = -1;
                    playerPieceIndex[1] = -1;
                    break;
                }

                if(inputMatrix[j][i] == playerPiece) {
                    playerPieceIndex[0] = j;
                    playerPieceIndex[1] = i;
                }
            }
        }
        if(spaceCount == 2 && playerPieceIndex[0] != -1 && playerPieceIndex[1] != -1) return playerPieceIndex;
        return new int[] {-1, -1};
    }
    private int[] indexIfCanPlaceInCenter(char[][] inputMatrix) {
        if(inputMatrix[1][1] == ' '){
            return new int[] {1, 1};
        }
        else{
            return new int[] {-1, -1};
        }
    }
    private int[] indexIfCanPlaceInOppositeCorner(char[][] inputMatrix) {
        int[] topLeftCorner = new int[] {0, 0};
        int[] topRightCorner = new int[] {0, 2};
        int[] bottomLeftCorner = new int[] {2, 0};
        int[] bottomRightCorner = new int[] {2, 2};
        if(inputMatrix[topLeftCorner[0]][topLeftCorner[1]] == 'x' && inputMatrix[bottomRightCorner[0]][bottomRightCorner[1]] == ' ') {
            return bottomRightCorner;
        }
        if(inputMatrix[bottomRightCorner[0]][bottomRightCorner[1]] == 'x' && inputMatrix[topLeftCorner[0]][topLeftCorner[1]] == ' ') {
            return topLeftCorner;
        }
        if(inputMatrix[topRightCorner[0]][topRightCorner[1]] == 'x' && inputMatrix[bottomLeftCorner[0]][bottomLeftCorner[1]] == ' ') {
            return bottomLeftCorner;
        }
        if(inputMatrix[bottomLeftCorner[0]][bottomLeftCorner[1]] == 'x' && inputMatrix[topRightCorner[0]][topRightCorner[1]] == ' ') {
            return topRightCorner;
        }
        return new int[] {-1, -1};
    }
    private int[] indexIfCanPlaceInCorner(char[][] inputMatrix) {
        if(inputMatrix[0][0] == ' ') return new int[] {0, 0};
        if(inputMatrix[0][2] == ' ') return new int[] {0, 2};
        if(inputMatrix[2][0] == ' ') return new int[] {2, 0};
        if(inputMatrix[2][2] == ' ') return new int[] {2, 2};
        return new int[] {-1, -1};
    }
    private int[] indexIfCanPlaceInCenterOfSide(char[][] inputMatrix) {
        if(inputMatrix[0][1] == ' ') return new int[] {0, 1};
        if(inputMatrix[1][0] == ' ') return new int[] {1, 0};
        if(inputMatrix[1][2] == ' ') return new int[] {1, 2};
        if(inputMatrix[2][1] == ' ') return new int[] {2, 1};
        return new int[] {-1, -1};
    }


   // Matrix Utilities
    public char[][] convertStringToSquareMatrix(String inputString){
       List<Character> inputCharacters = inputString.chars().mapToObj(e -> (char) e).collect(Collectors.toList());
       try {
           if (ensureValidInputString(inputCharacters)) {
               char[][] inputMatrix = convertListToMatrix(inputCharacters);
               if (checkForTurnOfO(inputString)) {
                   return inputMatrix;
               }
           }
       } catch(InvalidParameterException e) {
           throw new InvalidParameterException(e.getMessage());
       }
       return new char[0][0];
   }
    private char[][] cloneMatrix(char[][] inputMatrix) {
        char[][] returnMatrix = new char[inputMatrix.length][inputMatrix[0].length];
        for(int i = 0; i < inputMatrix.length; i++){
            for(int j = 0; j < inputMatrix[i].length; j++) {
                returnMatrix[i][j] = inputMatrix[i][j];
            }
        }
        return returnMatrix;
    }
    private boolean checkIfMatrixIsEqual(char[][] matrix1, char[][] matrix2) {
        if(matrix1.length != matrix2.length) return false;
        for(int i = 0; i < matrix1.length; i++){
            if(matrix2[i].length != matrix1[i].length) return false;
            for(int j = 0; j < matrix1[i].length; j++) {
                if(matrix1[i][j] != matrix2[i][j]) return false;
            }
        }
        return true;
    }
    private char[][] convertListToMatrix(List<Character> characterList) {
        int l = characterList.size();
        int k = 0, row, column;
        row = (int) Math.floor(Math.sqrt(l));
        column = (int) Math.ceil(Math.sqrt(l));

        if (row * column < l)
        {
            row = column;
        }


        char[][] returnArray = new char[row][column];
        // convert the string into grid
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < column; j++)
            {
                if(k < characterList.size())
                    returnArray[i][j] = characterList.get(k);
                k++;
            }
        }
        return returnArray;
    }
    private String convertMatrixToString(char[][] inputMatrix) {
        StringBuilder returnString = new StringBuilder();
        for(int i = 0; i < inputMatrix.length; i++) {
            for(int j = 0; j < inputMatrix[i].length; j++){
                returnString.append(inputMatrix[i][j]);
            }
        }
        return returnString.toString();
    }


    // Verification Utilities
    private boolean ensureValidInputString(List<Character> inputCharacters) {
        if(inputCharacters.size() != 9) return false;
        for(char input : inputCharacters)
        {
            switch(input) {
                case 'x':
                case 'o':
                case ' ':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }
    private boolean checkForTurnOfO(String inputString) {
        int x = 0;
        int o = 0;
        for(char character : inputString.toLowerCase().toCharArray()){
            if(character == 'x') {
                x++;
            } else if(character == 'o') {
                o++;
            }
        }
        return o <= x;
    }
    private boolean hasValidIndex(int[] index) {
        return index[0] != -1 && index[1] != -1;
    }

}
