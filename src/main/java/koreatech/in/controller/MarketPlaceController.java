package koreatech.in.controller;

import io.swagger.annotations.*;
import koreatech.in.annotation.Auth;
import koreatech.in.annotation.AuthExcept;
import koreatech.in.annotation.ParamValid;
import koreatech.in.annotation.ValidationGroups;
import koreatech.in.domain.Criteria.Criteria;
import koreatech.in.domain.MarketPlace.Item;
import koreatech.in.domain.MarketPlace.ItemComment;
import koreatech.in.service.MarketPlaceService;
import koreatech.in.util.StringXssChecker;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@Auth(role = Auth.Role.USER)
@Controller
public class MarketPlaceController {
    // TODO : getItemList 등 명칭은 service 까지는 laravel 메소드와 일치하도록 수정
    @Inject
    private MarketPlaceService marketPlaceService;

    @AuthExcept
    @RequestMapping(value = "/market/items", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getItems(@ApiParam(value = "서비스 타입(0: 팝니다 서비스, 1: 삽니다 서비스)", required = false) @RequestParam(value = "type", required = false, defaultValue="3") int type,
                            @ModelAttribute("criteria") Criteria criteria) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.getItems(type, criteria), HttpStatus.OK);
    }

    @ParamValid
    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createItem(@ApiParam(value = "(required: type, title), (optional: content, price, phone, is_phone_open, thumbnail)",required = true) @RequestBody @Validated(ValidationGroups.Create.class) Item item, BindingResult bindingResult) throws Exception {
        Item clear = new Item();
        return new ResponseEntity<Item>(marketPlaceService.createItem((Item) StringXssChecker.xssCheck(item, clear)), HttpStatus.CREATED);
    }

    @AuthExcept
    @RequestMapping(value = "/market/items/{id}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getItem(@ApiParam(required = true) @PathVariable(value = "id") int id) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.getItem(id), HttpStatus.OK);
    }

    @ParamValid
    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{id}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity updateItem(@ApiParam(value = "(optional: title, content, state, price, phone, is_phone_open, thumbnail)",required = true) @RequestBody @Validated(ValidationGroups.Update.class) Item item, BindingResult bindingResult, @ApiParam(required = true) @PathVariable(value = "id") int id) throws Exception {
        Item clear = new Item();
        return new ResponseEntity<Item>(marketPlaceService.updateItem((Item) StringXssChecker.xssCheck(item, clear), id), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{id}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity deleteItem(@ApiParam(required = true) @PathVariable(value = "id") int id) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.deleteItem(id), HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{id}/state", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity updateStateOfItem(@ApiParam(value = "(required: state)", required = true) @RequestBody Item state, @ApiParam(required = true) @PathVariable(value = "id") int id) throws Exception {

        return new ResponseEntity<Item>(marketPlaceService.updateStateOfItem(state.getState(), id), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/my/items", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getMyItemList(@ApiParam(value = "서비스 타입(0: 팝니다 서비스, 1: 삽니다 서비스)", required = true) @RequestParam(value = "type") int type,
                                 @ModelAttribute("criteria") Criteria criteria) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.getMyItemList(type, criteria), HttpStatus.OK);
    }

    @ParamValid
    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{itemId}/comments", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity createItemComment(@ApiParam(value = "(optional: content)", required = false) @RequestBody @Validated(ValidationGroups.Create.class) ItemComment itemComment, BindingResult bindingResult, @ApiParam(required = true) @PathVariable(value = "itemId") int item_id) throws Exception {
        ItemComment clear = new ItemComment();
        return new ResponseEntity<ItemComment>(marketPlaceService.createItemComment((ItemComment)StringXssChecker.xssCheck(itemComment, clear), item_id), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{itemId}/comments/{commentId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity getItemComment(@ApiParam(required = true) @PathVariable(value = "itemId") int itemId, @ApiParam(required = true) @PathVariable(value = "commentId") int commentId) throws Exception {

        return new ResponseEntity<ItemComment>(marketPlaceService.getItemComment(itemId, commentId), HttpStatus.OK);
    }

    @ParamValid
    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{itemId}/comments/{commentId}", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity updateItemComment(@ApiParam(value = "(optional: content)", required = false) @RequestBody @Validated(ValidationGroups.Update.class) ItemComment itemComment, BindingResult bindingResult, @ApiParam(required = true) @PathVariable(value = "itemId") int itemId, @ApiParam(required = true) @PathVariable(value = "commentId") int commentId) throws Exception {
        ItemComment clear = new ItemComment();
        return new ResponseEntity<ItemComment>(marketPlaceService.updateItemComment((ItemComment)StringXssChecker.xssCheck(itemComment, clear), itemId, commentId), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/{itemId}/comments/{commentId}", method = RequestMethod.DELETE)
    public @ResponseBody
    ResponseEntity deleteItemComment(@ApiParam(required = true) @PathVariable(value = "itemId") int itemId, @ApiParam(required = true) @PathVariable(value = "commentId") int commentId) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.deleteItemComment(itemId, commentId), HttpStatus.OK);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/grant/check", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity checkGrantEditItem(@ApiParam(required = true) @RequestBody Map<String, Integer> item_id) throws Exception {

        return new ResponseEntity<Map<String, Boolean>>(marketPlaceService.checkGrantEditItem(item_id.get("item_id")), HttpStatus.OK);
    }

    @ApiImplicitParams(
            @ApiImplicitParam(name = "mtfRequest", required = true, paramType = "form", dataType = "file")
    )
    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/image/upload", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity itemImagesUpload(@ApiParam(required = true) MultipartHttpServletRequest mtfRequest) throws Exception {
        Map<String, MultipartFile> fileMap = mtfRequest.getFileMap();

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.itemImagesUpload(fileMap), HttpStatus.CREATED);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value="Authorization")})
    @RequestMapping(value = "/market/items/image/thumbnail/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity itemThumbnailImageUpload(@ApiParam(required = true) MultipartFile image) throws Exception {

        return new ResponseEntity<Map<String, Object>>(marketPlaceService.itemThumbnailImageUpload(image), HttpStatus.CREATED);
    }
}