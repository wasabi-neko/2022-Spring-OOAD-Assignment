# 2022-Spring-OOAD Assignment

> this is a assignment from 2022-spring-OOAD
> Note: all the code from init commit is the "other people's code" in the assignment.

## Requirement

請幫這個 project 添加兩個新功能:

1. [x] 新增 Dependency 的 line (虛線箭頭)。
2. [x] 點擊 port 時,與此 port 相連的線會被 highlight。
    - 和字面意思一樣,點擊 port 時線才會被 highlight,點擊線時不會觸發
事件。
    - 點擊到沒有連接線的 port 時,也不會觸發事件。
    - 點擊其他地方,已 highlight 的線要取消 highlight。

若有其他 Bug 可以不予理會,也不需要改善程式架構,專注在加入新功能即可。

## Build & Run

Build:

```bash
ant
```

Run:

```bash
java -cp ./bin bgWork.Launcher
```
