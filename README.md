# fractjonal-calculator

For compsci, we were supposed to make a fractional calculator.
Being the stuck up teen that I am, I looked for a way to cheese the assignment.
Here was my first atempt:
[Nashorn.java](https://github.com/coalpha/cs-java/blob/2f2fbcaaa6606146e65b18757bc85983df316c17/FracCalc/Nashorn.java).
I leveraged the Nashorn JavaScript engine to parse the input and perform the
calculations for me. From what I remember, it didn't work all too well.
This was my second atempt. With [Joshua](https://github.com/legodude17)'s help
I read the [acorn source](https://github.com/acornjs/acorn/tree/master/acorn/src)
in order to learn more about how to parse text. He also helped me debug a good
bit of this.

This fractional calculator can handle order of operations for the operators
`+`, `-`, `*`, `/`, and `_`. It also understands parens.

