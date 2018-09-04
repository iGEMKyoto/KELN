# KELN
KELN (Kyoto-team Electronic Lab Note) is an useful app to create wiki for your iGem team. 

You can make html-formatted files with graphical user interface.

## For users
![main](https://raw.githubusercontent.com/iGEMKyoto/KELN/master/wiki/main.PNG)

## For admin
You can add or remove members and experiments by rewriting settings.txt

This file must be written with JSON format. Please be careful not to forget add , or "" .
![settings](https://raw.githubusercontent.com/iGEMKyoto/KELN/master/wiki/settings.PNG)

## For wiki master
1. Collect text files created by KELN and make sure that all of them are written with same character code (UTF-8 is recommended)
2. Combine and embed them into the html file.
3. Make css file for the beautiful appearance

elements for css

|type  |name  |
|---|---|
|div class|keln_container  |
|a class|kyoto-jump  |
|span class|keln_date  |
|span class|keln_exp  |
|span  |keln_researcher  |
|table class  |keln_table  |
