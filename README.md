# janalyzer

### 依赖
```java
//implementation 'com.github.linyuzai:janalyzer:0.1.0'
```

### 基本用法
```java
new MarkdownReader()
    .content()//md内容
    .file()//文件对象或路径
    .read()//执行解析
    .html()//转换到html域
    .render()//渲染成html
    .docx()//转换到docx域
    .write();//写文件
```