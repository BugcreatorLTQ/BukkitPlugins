# BukkitPlugins
## 后来写的很多代码不小心删掉了
## 暂时不会更新了
### 图像绘制
颜色填充算法：
问题：
已知$n$个方块的颜色`RGB[0,255][0,255][0,255]`
给出$m$个颜色`RGB`，计算与其颜色最接近的方块。
数据范围：$0<n<100,0<m<1e8$
算法：
> 简述：预处理颜色数组，快速建立所有颜色与方块的对应关系，之后直接读取。
> 重点：颜色数组的预处理方式，采用动态规划减少时间。
> 效率：预处理时间复杂度O($n^3$)，读取时间复杂度O($m$)，综合时间复杂度O($n^3+m$)
> 空间：空间复杂度O($n^3$)
1. 压缩颜色区域，建立映射关系：
    ```
    colorSpace[256][256][256] => colorSpace[n][n][n]
    ```
2. 将$n$个方块所对应颜色坐标入队：
    ```
    queue.push(n colors)
    ```
3. 取队首将其周围6个方向的**从未入队**的**相邻合法**方块，将这些方块的颜色设置为**出队方块颜色**，并将其入队，当队空时完成颜色填充：
    ```
    while(queue not empty){
        color = queue.pop()
        colors[] = [all colors about color]
        colors.setColor(color)
        queue.push(colors)
    }
    ```
4. 对$m$个颜色取值：
    ```
    colorSpace[r][g][b] => colorSpace[r/n][g/n][b/n]
    ```
