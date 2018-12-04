# FracCalc

- Class: "Hey make a Fractional Calculator"
- Me: "Oh that'll be easy, I'll just use the JavaScript Engine (Nashorn)"
- Class: "...You didn't really learn anything."
- Me: "That's the point"
- Class: "Do it without using Nashorn"
- Me: *poorly re-writes a JavaScript parser in Java*

This fractional calculator can handle order of operations for the operators
`+`, `-`, `*`, `/`, and `_`. It also understands parens.

### Usage (wtf_ja)

- Requires [`wtf_ja`](https://github.com/Coalpha/dotfiles/blob/master/bin/wtf_ja) and it's dependencies
- Basically, just install graalvm
- Run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -r -c:yes`
- Compile FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java --native:bin/FracCalc -c:yes`
- Compile and then run FracCalc: `wtf_ja -c:yes -i:src/FracCalc.java -n -r -c:yes`

# README
If you want to read the code, follow this path:

1. FracCalc
1. VT100.Colors
1. Acorn.README
1. Acorn.Tree
1. Acorn.Pestle.Token
1. Acorn.Pestle.TokenTypes
1. Acorn.Pestle.TokenType
1. Acorn.Pestle.Tokenizer
1. Acorn.Pestle.TokenList
1. Acorn.Pestle.TokenAndPosition
1. Acorn.Pestle.BinopCollection
1. Acorn.Tree
1. Acorn.Stove.Node
1. Acorn.Stove.Expression
1. Acorn.Stove.Literal
1. Acorn.Stove.ParenExpression
1. Acorn.Stove.BinopExpression
1. Acorn.Parser and Acorn.Pestle.TokenList
1. Acorn.Tree
