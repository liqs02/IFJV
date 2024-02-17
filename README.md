# Incredibly Fast Json Validator

Note: project is still in development. In the `issues` section, you can see future features.

IFJV provides fast json validation in java using a defined schema. The scalability of the algorithm is close to O(n),
where n is equal to the number of characters in the json. Currently it supports only strongly typed json schema.

## Schemas

IFJV schemas are similar to popular json validation schemas which are described in
detail [here](https://json-schema.org/draft/2020-12/json-schema-validation). However, it still doesn't support all of
these features. To define schemas we use `yaml` files.

| root instance types | is supported |
|:-------------------:|:------------:|
|       `array`       |    `true`    |
|      `boolean`      |    `true`    |
|       `const`       |   `false`    |
|       `enum`        |   `false`    |
|      `integer`      |    `true`    |
|      `number`       |    `true`    |
|      `object`       |    `true`    |
|      `string`       |    `true`    |

### Array Schema

|   keywords    | is supported | is required |
|:-------------:|:------------:|:-----------:|
|  `minItems`   |    `true`    |   `false`   |
|  `maxItems`   |    `true`    |   `false`   |
|    `items`    |    `true`    |   `true`    |
| `uniqueItems` |   `false`    |   `false`   |
| `minContains` |   `false`    |   `false`   |
| `maxContains` |   `false`    |   `false`   |

#### Examples

```yaml
type: array
items: number
```

```yaml
type: array
items:
  type: number
  minimum: 0
```

### Numbers Schemas (integer & number)

|      keywords      | is supported |
|:------------------:|:------------:|
|    `multipleOf`    |   `false`    |
|     `minimum`      |    `true`    |
|     `maximum`      |    `true`    |
| `exclusiveMinimum` |    `true`    |
| `exclusiveMaximum` |    `true`    |

#### Examples

```yaml
integer
```

```yaml
type: number
minimum: 1
exclusiveMaximum: 2.5
```

### Object Schema

|      keywords       | is supported | is required |
|:-------------------:|:------------:|:-----------:|
|   `minProperties`   |   `false`    |   `false`   |
|   `maxProperties`   |   `false`    |   `false`   |
|    `properties`     |    `true`    |   `true`    |
|     `required`      |    `true`    |   `false`   |
| `dependentRequired` |    `true`    |   `false`   |

#### Examples

```yaml
type: object
properties:
  num: integer
```

```yaml
type: object
properties:
  num:
    type: integer
    minimum: 0
```

### String Schema

|  keywords   | is supported |
|:-----------:|:------------:|
| `minLength` |    `true`    |
| `maxLength` |    `true`    |
|  `pattern`  |   `false`    |

#### Examples

```yaml
string
```

```yaml
type: string
minLength: 1
```

## Author

[@Patryk Likus](https://www.linkedin.com/in/patryklikus)

## License

All rights reserved. If you want to use it [contact me](https://www.linkedin.com/in/patryklikus).
