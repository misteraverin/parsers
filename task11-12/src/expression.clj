;(ns expression)

;common constructor

(defn CommonConstructor [evaluateOperation diffOperation toStringOperation]
  (fn [f]
    (f evaluateOperation diffOperation toStringOperation)))

(def zero
  (CommonConstructor
    (constantly 0)
    (constantly zero)
    "0"))

(defn Constant [n]
  (CommonConstructor
    (constantly n)
    (constantly zero)
    (str n)))
(def one (Constant 1))

(defn Variable [s]
  (CommonConstructor
    (fn [vars] (get vars s))
    (fn [var] (if (= s var) one zero))
    s))

(defn evaluate [expression vars]
  (expression (fn [f empty empty] (f vars))))

(defn diff [expression var]
  (expression (fn [empty f empty] (f var))))

(defn toString [expression]
  (expression (fn [empty empty s] s)))

;operations


(defn ApplyFunction [function arguments]
  (fn [expression]
    (function expression arguments)))

;evaluate, diff, toString

(defn CommonFunction [op diff string]
  (fn [& args]
    (fn [function]
      (function
        (fn [vars] (apply op (mapv (ApplyFunction evaluate vars) args)))
        (fn [var] (apply diff var args))
        (str "(" string " " (clojure.string/join " " (mapv toString args)) ")")))))

(def Negate
  (CommonFunction
    -
    (fn [var arg] (Negate (diff arg var)))
    "unary -"))

(def Add
  (CommonFunction
    +
    (fn [var & args] (apply Add (mapv (ApplyFunction diff var) args))) ;diff both args
    "+"))

(def Subtract
  (CommonFunction
    -
    (fn [var & args] (apply Subtract (mapv (ApplyFunction diff var) args)))
    "-"))


;Multiplication
(declare Multiply)

(def multDiff
  (fn
    ([var arg] (diff arg var))
    ([var a & args]
     (Add
       (Multiply
         (diff a var)
         (apply Multiply args))
       (Multiply
         a
         (diff (apply Multiply args) var))))))

(def Multiply
  (CommonFunction
    *
    multDiff
    "*"))

;Division
(declare Divide)

(def divideDiff
  (fn [var enum denom]
    (Divide
      (Subtract
        (Multiply (diff enum var) denom)
        (Multiply enum (diff denom var)))
      (Multiply denom denom)))
  )

(def Divide
  (CommonFunction
    /
    divideDiff
    "/"))

;Sin
(declare Cos)

(def sinDiff
  (fn [var arg]
    (Multiply
      (Cos arg)
      (diff arg var)))
  )

(def Sin
  (CommonFunction
    (fn [x] (Math/sin x))
    sinDiff
    "sin"))

;Cos
(def cosDiff
  (fn [var arg]
    (Multiply
      (Negate (Sin arg))
      (diff arg var)))
  )

(def Cos
  (CommonFunction
    (fn [x] (Math/cos x))
    cosDiff
    "cos"))


;prefix parser
(defn parseObject [s]
  (def ListOfFunctions {"+" Add "-" Subtract "*" Multiply "/" Divide "negate" Negate "sin" Sin "cos" Cos})
  (declare parse)
  (defn RecurrParse [name args]
    (apply (get ListOfFunctions (str name)) (map parse args)))
  (defn parse [x]
    (cond
      (symbol? x) (Variable (str x))
      (number? x) (Constant x)
      :else (RecurrParse (first x) (rest x))))
  (parse (read-string s)))


;(println (toString (diff Add (Variable "x") (Constant 2.0) "x")))

;(println (toString (parseObjectInfix "2 * x - 3")))
;(println (toString (parseObjectInfix "2 * 2 * 2 * x * 2 * y * x - 3")))
;(println (toString (parseObjectInfix "2 * 3 / 3 - y + z + cos(x + y + z + m + n + d + sin(1 + 2 + 3 + 4 * 2 * y)) / x - 3")))